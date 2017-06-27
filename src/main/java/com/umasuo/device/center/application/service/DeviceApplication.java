package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceActivateResult;
import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.dto.mapper.DeviceMapper;
import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.domain.service.DeviceService;
import com.umasuo.device.center.domain.service.UnionDeviceService;
import com.umasuo.device.center.infrastructure.enums.DeviceStatus;
import com.umasuo.device.center.infrastructure.exception.AlreadyBoundException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/5.
 */
@Service
public class DeviceApplication {

  private final static Logger logger = LoggerFactory.getLogger(DeviceApplication.class);

  /**
   * device publicKey prefix.
   */
  private final static String DEVICE_PUBLIC_KEY_PREFIX = "device:public:key:";

  /**
   * device service.
   */
  @Autowired
  private transient DeviceService deviceService;

  /**
   * The Token application.
   */
  @Autowired
  private TokenApplication tokenApplication;

  @Autowired
  private transient UnionDeviceService unionDeviceService;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  @Autowired
  private transient MessageApplication messageApplication;

  /**
   * 激活设备，与用户绑定。
   *
   * @param draft the draft
   */
  public DeviceActivateResult activate(DeviceDraft draft, String userId) {
    logger.info("Enter. deviceDraft: {}.", draft);

    // 0. unionId 是否存在
    UnionDevice unionDevice = unionDeviceService.findOne(draft.getUnionId());
    if (unionDevice == null) {
      logger.debug("Device: {} does not exist.", draft.getUnionId());
      throw new NotExistException("Device not exist");
    }

    String developerId = unionDevice.getDeveloperId();

    // 1. 根据unionId查询设备是否处于绑定状态
    if (deviceService.isDeviceBound(draft.getUnionId())) {
      logger.debug("Device: {} has bean bound.", draft.getUnionId());
      throw new AlreadyBoundException("Device has bean bound");
    }

    // 2. 检查userId和token是否匹配
    tokenApplication.validateToken(userId, draft.getToken());

    // 3. 根据userId获取developerId


    // 4. 新建device信息，并且与userId绑定
    Device device = deviceService.findByUserAndUnionId(userId, draft.getUnionId());

    if (device == null) {
      device = DeviceMapper.build(draft, userId, developerId);
    }

    // 5. 修改设备状态为 绑定
    device.setStatus(DeviceStatus.BIND);
    deviceService.save(device);

    // 6. 保存deviceId为key，publicKey为value的redis数据
    redisTemplate.opsForValue().set(DEVICE_PUBLIC_KEY_PREFIX + device.getDeviceId(),
        device.getPublicKey());

    DeviceActivateResult result = DeviceActivateResult.build(device);

    //为设备添加权限.
    messageApplication.addDeviceUser(result.getDeviceId(), draft.getToken());
    //发布消息通知客户端
    messageApplication.publish(result.getDeviceId(), userId);

    return result;
  }

  /**
   * 接触设备与用户的绑定关系。
   *
   * @param userId  the user id
   * @param unionId the union id
   */
  public void unbind(String userId, String unionId) {
    logger.debug("Enter. userId: {}, unionId: {}.", userId, unionId);

    Device device = deviceService.findByUserAndUnionId(userId, unionId);

    if (device == null) {
      logger.debug("User: {} does not have device: {}.", userId, unionId);
      throw new NotExistException("Can not find device");
    }

    device.setStatus(DeviceStatus.UNBIND);
    deviceService.save(device);

    logger.debug("Exit.");
  }

  /**
   * get one device by device id.
   *
   * @param deviceId    String
   * @param developerId the developer id
   * @param userId      the user id
   * @return DeviceView by device id
   */
  public DeviceView getByDeviceId(String deviceId, String developerId, String userId) {
    logger.debug("Enter. deviceId: {}.", deviceId);

    Device device = deviceService.get(deviceId);

    if (!device.getDeveloperId().equals(developerId)) {
      logger.debug("Device: {} is not belong to developer: {}.", deviceId, developerId);
      throw new ParametersException("The device not belong to the developer: " + developerId + "," +
          " deviceId: " + deviceId);
    }

    if (StringUtils.isNotBlank(userId) &&
        !userId.equals(device.getOwnerId())) {
      logger.debug("Device: {} is not belong to user: {}.", deviceId, userId);
      throw new ParametersException("The device not belong to the user: " + userId + "," +
          " deviceId: " + deviceId);
    }

    DeviceView view = DeviceMapper.toView(device);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * 获取一个用户在某个开发者下的所有设备。
   *
   * @param userId      the user id
   * @param developerId the developer id
   * @return 设备列表 by user and developer
   */
  public List<DeviceView> getByUserAndDeveloper(String userId, String developerId) {
    logger.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    List<Device> devices = deviceService.getByUserAndDeveloper(userId, developerId);
    List<DeviceView> views = DeviceMapper.toView(devices);

    logger.debug("Exit. viewSize: {}.", views.size());
    logger.trace("DeviceView: {}.", views);
    return views;
  }

  /**
   * Gets by user and definition.
   *
   * @param userId             the user id
   * @param developerId        the developer id
   * @param deviceDefinitionId the device definition id
   * @return the by user and definition
   */
  public DeviceView getByUserAndDefinition(String userId, String developerId,
                                           String deviceDefinitionId) {
    logger.debug("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.", userId, developerId,
        deviceDefinitionId);

    Device device = deviceService.getByUserAndDefinition(userId, developerId, deviceDefinitionId);
    if (device == null) {
      logger.debug("Can not find device by user: {}, developer: {}, deviceDefinition: {}.",
          userId, developerId, deviceDefinitionId);
      throw new NotExistException("Device not find");
    }
    DeviceView result = DeviceMapper.toView(device);

    logger.debug("Exit. device: {}.", result);

    return result;
  }
}
