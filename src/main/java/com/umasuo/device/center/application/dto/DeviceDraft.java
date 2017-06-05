package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/5.
 */
@Data
public class DeviceDraft implements Serializable {

  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 8800732225747388907L;

  /**
   * device definition id.
   */
  @NotNull
  private String deviceDefineId;

  private String customizedId;

  private String userId;

  /**
   * 冗余数据，用于做统计、报表、管理等.
   */
  @NotNull
  private String developerId;
}
