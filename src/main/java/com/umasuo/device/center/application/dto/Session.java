package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/7/3.
 * Device session. used in cache.
 */
@Data
public class Session implements Serializable {
  private static final long serialVersionUID = -6621681640958400777L;

  private long lastUpdateTime;


  private DeviceView device;
}
