package com.umasuo.device.center.domain.service;

import com.google.common.collect.Lists;
import com.umasuo.device.center.application.dto.UnionDeviceRequest;
import com.umasuo.device.center.application.dto.UnionDeviceView;
import com.umasuo.device.center.application.dto.UnionRegisterRequest;
import com.umasuo.device.center.application.dto.mapper.UnionMapper;
import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.infrastructure.repository.UnionDeviceRepository;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Davis on 17/6/27.
 */
@Service
public class UnionDeviceService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UnionDeviceService.class);

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
    LOG.debug("Enter. unionDevice size.", unionDevices.size());

    repository.save(unionDevices);

    LOG.debug("Exit.");
  }

  @Async
  public UnionDevice save(UnionDevice unionDevice) {
    LOG.debug("Enter. unionDevice: {}.", unionDevice);

    UnionDevice savedUnionDevice = repository.save(unionDevice);

    LOG.debug("Exit.");

    return savedUnionDevice;
  }

  /**
   * 判断union id是否存在
   *
   * @param unionId
   * @return
   */
  public boolean isUnionIdExist(String unionId) {
    LOG.debug("Enter. unionId: {}.", unionId);

    boolean result = repository.exists(unionId);

    LOG.debug("Exit. unionId exist? {}", result);

    return result;
  }

  public UnionDevice findOne(String unionId) {
    LOG.debug("Enter. unionId: {}.", unionId);

    UnionDevice unionDevice = repository.findOne(unionId);
    if (unionDevice == null) {
      LOG.debug("Device: {} does not exist.", unionDevice);
      throw new NotExistException("Device not exist");
    }
    LOG.debug("Exit. unionDevice: {}.", unionDevice);

    return unionDevice;
  }
}
