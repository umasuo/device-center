package com.umasuo.device.center.domain.service;

import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.infrastructure.enums.DeviceStatus;
import com.umasuo.device.center.infrastructure.repository.DeviceRepository;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

  /**
   * The DeviceRepository.
   */
  @Autowired
  private transient DeviceRepository deviceRepository;

  /**
   * save a device.
   *
   * @param sample the sample
   * @return device device
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
   * @param id the id
   * @return device device
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
   * @param userId userID
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
   * @param deviceDefineId the device define id
   * @return list of device
   */
  public List<Device> getByDeviceDefineId(String deviceDefineId) {
    logger.debug("Enter. productId: {}.", deviceDefineId);

    Device device = new Device();
    device.setProductId(deviceDefineId);
    Example<Device> example = Example.of(device);
    List<Device> devices = deviceRepository.findAll(example);

    logger.debug("Exit. count: {}.", devices);
    return devices;
  }

  /**
   * Get device by user and device definition.
   *
   * @param userId the user id
   * @param developerId the developer id
   * @param deviceDefinitionId the device definition id
   * @return Device by user and definition
   */
  public Device getByUserAndDefinition(String userId, String developerId,
      String deviceDefinitionId) {
    logger.debug("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.", userId, developerId,
        deviceDefinitionId);

    Device sample = new Device();
    sample.setProductId(deviceDefinitionId);
    sample.setOwnerId(userId);
    sample.setDeveloperId(developerId);

    Example<Device> example = Example.of(sample);

    Device device = deviceRepository.findOne(example);

    logger.debug("Exit. device: {}.", device);

    return device;
  }


  /**
   * Gets all report.
   *
   * @return the all report
   */
  public List<HashMap> getTotalReport() {
    logger.debug("Enter.");

    List<HashMap> result = deviceRepository.getReport();

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets developer all report.
   *
   * @param developerId the developer id
   * @return the developer all report
   */
  public List<HashMap> getTotalReport(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<HashMap> result = deviceRepository.getReport(developerId);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * Gets registered report.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the registered report
   */
  public List<HashMap> getIncreaseReport(long startTime, long endTime) {
    logger.debug("Enter.");

    List<HashMap> result = deviceRepository.getIncreaseReport(startTime, endTime);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }



  /**
   * Gets developer registered report.
   *
   * @param developerId the developer id
   * @param startTime the start time
   * @return the developer registered report
   */
  public List<HashMap> getIncreaseReport(String developerId, long startTime) {
    logger.debug("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    List<HashMap> result = deviceRepository.getDeveloperRegisterReport(developerId, startTime);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * 检查设备是否已经被绑定。
   *
   * @param unionId union id
   * @return
   */
  public boolean isDeviceBound(String unionId) {
    logger.debug("Enter. unionId: {}.", unionId);

    Device sample = new Device();
    sample.setUnionId(unionId);
    sample.setStatus(DeviceStatus.BIND);

    Example<Device> example = Example.of(sample);

    boolean result = deviceRepository.exists(example);

    logger.debug("Exit. isDeviceBound? {}", result);

    return result;
  }

  /**
   * 根据用户id和设备id获取设备信息。
   *
   * @param userId
   * @param unionId
   * @return
   */
  public Device findByUserAndUnionId(String userId, String unionId) {
    logger.debug("Enter. userId: {}, unionId: {}.", userId, unionId);

    Device sample = new Device();
    sample.setUnionId(unionId);
    sample.setOwnerId(userId);

    Example<Device> example = Example.of(sample);

    Device result = deviceRepository.findOne(example);

    logger.debug("Exit. device: {}.", result);

    return result;
  }

  public Device findByUserAndDeviceId(String userId, String deviceId) {
    logger.debug("Enter. userId: {}, deviceId: {}.", userId, deviceId);

    Device sample = new Device();
    sample.setDeveloperId(deviceId);
    sample.setOwnerId(userId);

    Example<Device> example = Example.of(sample);

    Device result = deviceRepository.findOne(example);

    logger.debug("Exit. device: {}.", result);

    return result;
  }
}
