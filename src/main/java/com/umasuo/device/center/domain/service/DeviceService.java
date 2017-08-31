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
 * Device service.
 */
@Service
public class DeviceService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

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
    LOGGER.debug("Enter. sample: {}.", sample);

    Device saved = deviceRepository.save(sample);

    LOGGER.debug("Exit. savedDevice: {}.", saved);
    return saved;
  }

  /**
   * Get Device by device id.
   *
   * @param id the id
   * @return device device
   */
  public Device get(String id) {
    LOGGER.debug("Enter. id: {}.", id);

    Device device = deviceRepository.findOne(id);
    if (device == null) {
      LOGGER.debug("Device: {} not exist.", id);
      throw new NotExistException("Device not exist for id: " + id);
    }
    LOGGER.debug("Exit. device: {}.", device);
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
    LOGGER.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    Device device = new Device();
    device.setOwnerId(userId);
    device.setDeveloperId(developerId);
    Example<Device> example = Example.of(device);
    List<Device> devices = deviceRepository.findAll(example);

    LOGGER.debug("Exit. deviceSize: {}.", devices.size());
    LOGGER.debug("devices: {}.", devices);
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
    LOGGER.debug("Enter. productId: {}.", deviceDefineId);

    Device device = new Device();
    device.setProductId(deviceDefineId);
    Example<Device> example = Example.of(device);
    List<Device> devices = deviceRepository.findAll(example);

    LOGGER.debug("Exit. count: {}.", devices);
    return devices;
  }

  /**
   * Get device by user and device definition.
   *
   * @param userId             the user id
   * @param developerId        the developer id
   * @param deviceDefinitionId the device definition id
   * @return Device by user and definition
   */
  public Device getByUserAndDefinition(String userId, String developerId,
                                       String deviceDefinitionId) {
    LOGGER.debug("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.", userId, developerId,
      deviceDefinitionId);

    Device sample = new Device();
    sample.setProductId(deviceDefinitionId);
    sample.setOwnerId(userId);
    sample.setDeveloperId(developerId);

    Example<Device> example = Example.of(sample);

    Device device = deviceRepository.findOne(example);

    LOGGER.debug("Exit. device: {}.", device);

    return device;
  }


  /**
   * Gets all report.
   *
   * @return the all report
   */
  public List<HashMap> getTotalReport() {
    LOGGER.debug("Enter.");

    List<HashMap> result = deviceRepository.getReport();

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets developer all report.
   *
   * @param developerId the developer id
   * @return the developer all report
   */
  public List<HashMap> getTotalReport(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<HashMap> result = deviceRepository.getReport(developerId);

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * Gets registered report.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the registered report
   */
  public List<HashMap> getIncreaseReport(long startTime, long endTime) {
    LOGGER.debug("Enter.");

    List<HashMap> result = deviceRepository.getIncreaseReport(startTime, endTime);

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets developer registered report.
   *
   * @param developerId the developer id
   * @param startTime   the start time
   * @return the developer registered report
   */
  public List<HashMap> getIncreaseReport(String developerId, long startTime) {
    LOGGER.debug("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    List<HashMap> result = deviceRepository.getDeveloperRegisterReport(developerId, startTime);

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * 检查设备是否已经被绑定。
   *
   * @param unionId union id
   * @return
   */
  public boolean isDeviceBound(String unionId) {
    LOGGER.debug("Enter. unionId: {}.", unionId);

    Device sample = new Device();
    sample.setUnionId(unionId);
    sample.setStatus(DeviceStatus.BIND);

    Example<Device> example = Example.of(sample);

    boolean result = deviceRepository.exists(example);

    LOGGER.debug("Exit. isDeviceBound? {}", result);

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
    LOGGER.debug("Enter. userId: {}, unionId: {}.", userId, unionId);

    Device sample = new Device();
    sample.setUnionId(unionId);
    sample.setOwnerId(userId);

    Example<Device> example = Example.of(sample);

    Device result = deviceRepository.findOne(example);

    LOGGER.debug("Exit. device: {}.", result);

    return result;
  }

  /**
   * Find by user and device id.
   *
   * @param userId
   * @param deviceId
   * @return
   */
  public Device findByUserAndDeviceId(String userId, String deviceId) {
    LOGGER.debug("Enter. userId: {}, deviceId: {}.", userId, deviceId);

    Device sample = new Device();
    sample.setDeviceId(deviceId);
    sample.setOwnerId(userId);

    Example<Device> example = Example.of(sample);

    Device result = deviceRepository.findOne(example);

    LOGGER.debug("Exit. device: {}.", result);

    return result;
  }

  /**
   * Get by developer id.
   *
   * @param developerId
   * @return
   */
  public List<Device> getByDeveloper(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);
    Device sample = new Device();
    sample.setDeveloperId(developerId);

    Example<Device> example = Example.of(sample);
    List<Device> devices = deviceRepository.findAll(example);

    LOGGER.debug("Exit. device size: {}.", devices.size());

    return devices;
  }

  /**
   * Count device.
   *
   * @return
   */
  public Long countDevices() {
    LOGGER.info("Enter.");

    Long count = deviceRepository.count();

    LOGGER.debug("Exit. device count: {}.", count);

    return count;
  }
}
