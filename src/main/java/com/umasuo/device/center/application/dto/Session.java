package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.domain.model.Device;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/7/3.
 */
@Data
public class Session implements Serializable {
  private static final long serialVersionUID = -6621681640958400777L;

  // TODO: 17/7/3 command supported
  // TODO: 17/7/3 data supported
  // TODO: 17/7/3 Status
  private long lastUpdateTime;

  private DeviceView device;
}
