package com.umasuo.device.center.domain.service;

import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.infrastructure.repository.DeviceRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

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
   * 获取用户在某个开发者下的所有设备.
   *
   * @param userId      userID
   * @param developerId 开发者ID
   * @return list of device
   */
  public List<Device> getByUserAndDeveloper(String userId, String developerId) {
    logger.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    Device device = new Device();
    device.setOwnerId(userId);
    device.setDeveloperId(developerId);
    Example<Device> example = Example.of(device);
    List<Device> devices = deviceRepository.findAll(example);

    logger.debug("Enter. deviceSize: {}.", devices.size());
    logger.debug("devices: {}.", devices);
    return devices;
  }

  /**
   * 获取某种类型的设备的列表.
   * TODO 这个应该分页，否则会过多而死.
   *
   * @param deviceDefineId
   * @return list of device
   */
  public List<Device> getByDeviceDefineId(String deviceDefineId) {
    logger.debug("Enter. deviceDefineId: {}.", deviceDefineId);

    Device device = new Device();
    device.setDeviceDefineId(deviceDefineId);
    Example<Device> example = Example.of(device);
    List<Device> devices = deviceRepository.findAll(example);

    logger.debug("Exit. count: {}.", devices);
    return devices;
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

  /**
   * 统计device的数据.
   *
   * @param developerId
   * @return
   */
  public long countDevice(String developerId, String deviceDefinitionId, long start, long end) {
    return 0;
  }
}
