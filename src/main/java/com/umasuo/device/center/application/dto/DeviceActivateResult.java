package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.domain.model.Device;
import lombok.Data;

/**
 * Created by Davis on 17/6/27.
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

  public static DeviceActivateResult build(Device device) {
    DeviceActivateResult result = new DeviceActivateResult();

    result.setDeviceId(device.getDeviceId());
    result.setPublicKey(device.getPublicKey());

    return result;
  }
}
