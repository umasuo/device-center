package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by umasuo on 17/6/8.
 */
@Data
public class DeviceCount implements Serializable {
  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 1503878297410640012L;

  private String deviceId;

  private String deviceDefinitionId;

  /**
   * 时区.
   */
  private String timeZone;

}
