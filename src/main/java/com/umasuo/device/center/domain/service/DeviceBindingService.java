package com.umasuo.device.center.domain.service;

import com.umasuo.device.center.domain.model.DeviceBinding;
import com.umasuo.device.center.infrastructure.repository.DeviceBindingRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 设备绑定关系service.
 * Created by umasuo on 17/6/5.
 */
@Service
public class DeviceBindingService {

  private final static Logger logger = LoggerFactory.getLogger(DeviceBindingService.class);

  @Autowired
  private transient DeviceBindingRepository deviceBindingRepository;

  /**
   * save device binding.
   *
   * @param deviceBinding device binding
   * @return DeviceBinding
   */
  public DeviceBinding save(DeviceBinding deviceBinding) {
    logger.debug("Enter. deviceBinding: {}.", deviceBinding);

    DeviceBinding saved = deviceBindingRepository.save(deviceBinding);

    logger.debug("Exit. savedDeviceBiding: {}.", saved);
    return saved;
  }

  /**
   * get device binding by id.
   *
   * @param id String
   * @return DeviceBinding
   */
  public DeviceBinding get(String id) {
    logger.debug("Enter. id: {}.", id);

    DeviceBinding deviceBinding = deviceBindingRepository.findOne(id);
    if (deviceBinding == null) {
      throw new NotExistException("DeviceBiding not exits for id: " + id);
    }

    logger.debug("Exit. deviceBinding: {}.", deviceBinding);
    return deviceBinding;
  }

  /**
   * get one binding relationship by device id and user id.
   *
   * @param deviceId String
   * @param userId   String
   * @return DeviceBinding
   */
  public DeviceBinding get(String deviceId, String userId) {
    logger.debug("Enter. deviceId: {}, userId: {}.", deviceId, userId);

    DeviceBinding deviceBinding = new DeviceBinding();
    deviceBinding.setDeviceId(deviceId);
    deviceBinding.setUserId(userId);
    Example<DeviceBinding> example = Example.of(deviceBinding);
    DeviceBinding savedDeviceBinding = deviceBindingRepository.findOne(example);

    logger.debug("Exit. deviceBinding: {}.", savedDeviceBinding);
    return savedDeviceBinding;
  }
}
