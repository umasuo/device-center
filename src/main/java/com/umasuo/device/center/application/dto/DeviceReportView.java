package com.umasuo.device.center.application.dto;

import static java.lang.Math.toIntExact;

import lombok.Data;

/**
 * Created by Davis on 17/6/16.
 */
@Data
public class DeviceReportView {
  /**
   * The Device definition id.
   */
  private String deviceDefinitionId;

  /**
   * The developer id.
   */
  private String developerId;

  /**
   * The Register number.
   */
  private long registerNumber;

  /**
   * The Online number.
   */
  private long onlineNumber;

  /**
   * The Total number.
   */
  private long totalNumber;
}
