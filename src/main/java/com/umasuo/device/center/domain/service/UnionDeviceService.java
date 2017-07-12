package com.umasuo.device.center.domain.service;

import com.google.common.collect.Lists;
import com.umasuo.device.center.application.dto.UnionDeviceRequest;
import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.infrastructure.repository.UnionDeviceRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/27.
 */
@Service
public class UnionDeviceService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UnionDeviceService.class);

  /**
   * Token length.
   */
  private static final int SECRET_KEY_LENGTH = 7;

  @Autowired
  private transient UnionDeviceRepository repository;

  /**
   * 批量新建union id.
   *
   * @param developerId the developer id
   * @param request     the quantity
   * @return the list
   */
  public List<UnionDevice> batchCreate(String developerId, UnionDeviceRequest request) {
    LOG.debug("Enter. developerId: {}, request: {}.", developerId, request);

    List<UnionDevice> unionDevices = Lists.newArrayList();
    //todo 检查product 是否存在

    // todo 这将是一个漫长的请求
    for (int i = 0; i < request.getQuantity(); i++) {
      UnionDevice unionDevice = new UnionDevice();
      unionDevice.setDeveloperId(developerId);
      unionDevice.setProductId(request.getProductId());
      // set a random secret key
      unionDevice.setSecretKey(RandomStringUtils.randomAlphanumeric(SECRET_KEY_LENGTH));
      unionDevices.add(unionDevice);
    }

    List<UnionDevice> result = repository.save(unionDevices);

    LOG.debug("Exit.");

    return result;
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

    LOG.debug("Exit. unionDevice: {}.", unionDevice);

    return unionDevice;
  }
}
