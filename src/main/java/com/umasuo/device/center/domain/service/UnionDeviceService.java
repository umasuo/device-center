package com.umasuo.device.center.domain.service;

import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.infrastructure.repository.UnionDeviceRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Union Device service.
 */
@Service
public class UnionDeviceService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UnionDeviceService.class);

  /**
   * Union device repository.
   */
  @Autowired
  private transient UnionDeviceRepository repository;

  /**
   * 批量保存union id.
   *
   * @param unionDevices list of union device
   * @return the list
   */
  @Async
  public void save(List<UnionDevice> unionDevices) {
    LOGGER.debug("Enter. unionDevice size.", unionDevices.size());

    repository.save(unionDevices);

    LOGGER.debug("Exit.");
  }

  /**
   * Save union device.
   *
   * @param unionDevice
   * @return
   */
  @Async
  public UnionDevice save(UnionDevice unionDevice) {
    LOGGER.debug("Enter. unionDevice: {}.", unionDevice);

    UnionDevice savedUnionDevice = repository.save(unionDevice);

    LOGGER.debug("Exit.");

    return savedUnionDevice;
  }

  /**
   * 判断union id是否存在
   *
   * @param unionId
   * @return
   */
  public boolean isUnionIdExist(String unionId) {
    LOGGER.debug("Enter. unionId: {}.", unionId);

    boolean result = repository.exists(unionId);

    LOGGER.debug("Exit. unionId exist? {}", result);

    return result;
  }

  /**
   * Find one union device.
   *
   * @param unionId
   * @return
   */
  public UnionDevice findOne(String unionId) {
    LOGGER.debug("Enter. unionId: {}.", unionId);

    UnionDevice unionDevice = repository.findOne(unionId);
    if (unionDevice == null) {
      LOGGER.debug("Device: {} does not exist.", unionId);
      throw new NotExistException("Device not exist");
    }
    LOGGER.debug("Exit. unionDevice: {}.", unionDevice);

    return unionDevice;
  }
}
