package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceData;
import com.umasuo.device.center.application.service.DeviceApplication;
import com.umasuo.device.center.infrastructure.Router;
import com.umasuo.device.center.infrastructure.enums.DeviceStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/7/14.
 */
@CrossOrigin
@RestController
public class DeviceDataController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(DeviceDataController.class);

  @Autowired
  private transient DeviceApplication deviceApplication;

  // TODO: 17/7/13 查询参数， 分页，排序
  @GetMapping(value = Router.DEVICE_DATA)
  public List<DeviceData> getDeviceData(@RequestHeader String developerId,
      @RequestParam(required = false) String productId,
      @RequestParam(required = false) DeviceStatus status,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) String unionId,
      @RequestParam(required = false) String deviceId) {
    logger.info(
        "Enter. developerId: {}, productId: {}, status: {}, phone: {}, unionId: {}, deviceId: {}.",
        developerId, productId, status, phone, unionId, deviceId);

    List<DeviceData> deviceData = deviceApplication.getDeviceData(developerId);

    logger.info("Exit. deviceData size: {}.", deviceData.size());

    return deviceData;
  }
}
