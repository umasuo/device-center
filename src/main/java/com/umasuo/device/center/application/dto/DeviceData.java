package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.infrastructure.enums.DeviceStatus;

import lombok.Data;

/**
 * Created by Davis on 17/7/13.
 */
@Data
public class DeviceData {

  private String icon;

  private String deviceId;

  private String unionId;

  private String productName;

  private String userPhone;

  private DeviceStatus status;

  private Long activateTime;

  private Long bindTime;

  private Long unbindTime;
}
