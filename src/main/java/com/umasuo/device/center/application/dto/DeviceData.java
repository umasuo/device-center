package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.infrastructure.enums.DeviceStatus;
import lombok.Data;

/**
 * Device data.
 */
@Data
public class DeviceData {

  /**
   * Device icon url.
   */
  private String icon;

  /**
   * Device id.
   */
  private String deviceId;

  /**
   * Device union id,
   */
  private String unionId;

  /**
   * Product name.
   */
  private String productName;

  /**
   * User phone.
   */
  private String userPhone;

  /**
   * Device status.
   */
  private DeviceStatus status;

  /**
   * Device activate time.
   */
  private Long activateTime;

  /**
   * Device bind time.
   */
  private Long bindTime;

  /**
   * Device unbind time.s
   */
  private Long unbindTime;
}
