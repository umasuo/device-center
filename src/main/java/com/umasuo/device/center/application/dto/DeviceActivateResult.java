package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.domain.model.Device;
import lombok.Data;

/**
 * Device activate result.
 */
@Data
public class DeviceActivateResult {

  /**
   * Device id.
   */
  private String deviceId;

  /**
   * Device publicKey.
   */
  private String publicKey;

  /**
   * Build result.
   *
   * @param device
   * @return
   */
  public static DeviceActivateResult build(Device device) {
    DeviceActivateResult result = new DeviceActivateResult();

    result.setDeviceId(device.getDeviceId());
    result.setPublicKey(device.getPublicKey());

    return result;
  }
}
