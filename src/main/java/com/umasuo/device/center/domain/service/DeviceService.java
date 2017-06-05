package com.umasuo.device.center.domain.service;

import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.infrastructure.repository.DeviceRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/5.
 */
@Service
public class DeviceService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeviceService.class);

  @Autowired
  private transient DeviceRepository deviceRepository;


  /**
   * save a device.
   *
   * @param sample
   * @return
   */
  public Device save(Device sample) {
    logger.debug("Enter. sample: {}.", sample);

    Device saved = deviceRepository.save(sample);

    logger.debug("Exit. savedDevice: {}.", saved);
    return saved;
  }

  /**
   * Get Device by device id.
   *
   * @param id
   * @return
   */
  public Device get(String id) {
    logger.debug("Enter. id: {}.", id);

    Device device = deviceRepository.findOne(id);
    if (device == null) {
      throw new NotExistException("Device not exist for id: " + id);
    }
    logger.debug("Exit. device: {}.", id);
    return device;
  }

  /**
   * count device for a special device define.
   *
   * @param deviceDefineId device define id
   * @return count result
   */
  public long countByDeviceDefine(String deviceDefineId) {
    logger.debug("Enter. deviceDefineId: {}.", deviceDefineId);

    Device device = new Device();
    device.setDeviceDefineId(deviceDefineId);
    Example<Device> example = Example.of(device);
    long count = deviceRepository.count(example);

    logger.debug("Exit. count: {}.", count);
    return count;
  }
}
