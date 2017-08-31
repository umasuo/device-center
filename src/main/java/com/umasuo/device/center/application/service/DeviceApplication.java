package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceActivateResult;
import com.umasuo.device.center.application.dto.DeviceData;
import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.dto.mapper.DeviceDataMapper;
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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Device application.
 */
@Service
public class DeviceApplication {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeviceApplication.class);

  /**
   * device service.
   */
  @Autowired
  private transient DeviceService deviceService;

  /**
   * Union device service.
   */
  @Autowired
  private transient UnionDeviceService unionDeviceService;

  /**
   * Message app.
   */
  @Autowired
  private transient MessageApplication messageApplication;

  /**
   * Session app.
   */
  @Autowired
  private transient SessionApplication sessionApplication;

  /**
   * 激活设备，与用户绑定。
   *
   * @param draft the draft
   */
  public DeviceActivateResult activate(DeviceDraft draft, String userId) {
    LOGGER.info("Enter. deviceDraft: {}.", draft);

    // 0. unionId 是否存在
    UnionDevice unionDevice = unionDeviceService.findOne(draft.getUnionId());
    if (!unionDevice.getProductId().equals(draft.getProductId())) {
      LOGGER.debug("Device: {} not belong to the kind of product: {}.", draft.getUnionId(), draft
        .getProductId());
      throw new NotExistException("Device not belong to the product.");
    }


    // 1. 根据unionId查询设备是否处于绑定状态
    if (deviceService.isDeviceBound(draft.getUnionId())) {
      LOGGER.debug("Device: {} has bean bound.", draft.getUnionId());
      throw new AlreadyBoundException("Device has bean bound");
    }

    // 暂时不需要token，先去掉
    // 2. 检查userId和token是否匹配
//    tokenApplication.validateToken(userId, draft.getToken());
    String developerId = unionDevice.getDeveloperId();
    //TODO 3. 根据userId获取developerId

    // 4. 新建device信息，并且与userId绑定
    Device device = deviceService.findByUserAndUnionId(userId, draft.getUnionId());
    if (device == null) {
      device = DeviceMapper.toModel(draft, userId, developerId);
    }

    // 5. 修改设备状态为 绑定
    device.setStatus(DeviceStatus.BIND);
    deviceService.save(device);

    DeviceActivateResult result = DeviceActivateResult.build(device);

    //添加MQTT的用户名和密码
    messageApplication.addMqttUser(result.getDeviceId(), result.getPublicKey());
    // todo 发布消息通知客户端设备已经激活
//    messageApplication.publish(result.getDeviceId(), userId);
    //更新设备的session
    sessionApplication.updateSession(device);

    return result;
  }

  /**
   * 接触设备与用户的绑定关系。
   *
   * @param userId   the user id
   * @param deviceId the device id
   */
  public void unbind(String userId, String deviceId) {
    LOGGER.debug("Enter. userId: {}, deviceId: {}.", userId, deviceId);

    Device device = deviceService.findByUserAndDeviceId(userId, deviceId);

    if (device == null) {
      LOGGER.debug("User: {} does not have device: {}.", userId, deviceId);
      throw new NotExistException("Can not find device");
    }

    device.setStatus(DeviceStatus.UNBIND);
    deviceService.save(device);

    // TODO: 17/6/28 给设备发送信息，清空设备上保存的用户信息

    sessionApplication.clearSession(device.getDeveloperId(), device.getDeviceId());

    LOGGER.debug("Exit.");
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
    LOGGER.debug("Enter. deviceId: {}.", deviceId);

    Device device = deviceService.get(deviceId);

    if (!device.getDeveloperId().equals(developerId)) {
      LOGGER.debug("Device: {} is not belong to developer: {}.", deviceId, developerId);
      throw new ParametersException("The device not belong to the developer: " + developerId + "," +
        " deviceId: " + deviceId);
    }

    if (StringUtils.isNotBlank(userId) &&
      !userId.equals(device.getOwnerId())) {
      LOGGER.debug("Device: {} is not belong to user: {}.", deviceId, userId);
      throw new ParametersException("The device not belong to the user: " + userId + "," +
        " deviceId: " + deviceId);
    }

    DeviceView view = DeviceMapper.toView(device);

    LOGGER.debug("Exit. deviceView: {}.", view);
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
    LOGGER.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    List<Device> devices = deviceService.getByUserAndDeveloper(userId, developerId);
    List<DeviceView> views = DeviceMapper.toView(devices);

    LOGGER.debug("Exit. viewSize: {}.", views.size());
    LOGGER.trace("DeviceView: {}.", views);
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
    LOGGER.debug("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.", userId, developerId,
      deviceDefinitionId);

    Device device = deviceService.getByUserAndDefinition(userId, developerId, deviceDefinitionId);
    if (device == null) {
      LOGGER.debug("Can not find device by user: {}, developer: {}, deviceDefinition: {}.",
        userId, developerId, deviceDefinitionId);
      throw new NotExistException("Device not find");
    }
    DeviceView result = DeviceMapper.toView(device);

    LOGGER.debug("Exit. device: {}.", result);

    return result;
  }

  /**
   * 获取设备的运营数据。
   *
   * @param developerId
   * @return
   */
  public List<DeviceData> getDeviceData(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Device> devices = deviceService.getByDeveloper(developerId);

    List<DeviceData> deviceData = DeviceDataMapper.toModel(devices);
    // TODO: 17/7/13 获取用户手机和产品名称

    LOGGER.debug("Exit. deviceData size: {}.", deviceData.size());

    return deviceData;
  }

  /**
   * Count devices.
   *
   * @return
   */
  public Long countDevices() {
    LOGGER.debug("Enter.");

    Long count = deviceService.countDevices();

    LOGGER.debug("Exit. device count: {}.", count);

    return count;
  }
}
