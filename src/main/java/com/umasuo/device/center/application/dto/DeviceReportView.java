package com.umasuo.device.center.application.dto;

import lombok.Data;

/**
 * Device report view.
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
