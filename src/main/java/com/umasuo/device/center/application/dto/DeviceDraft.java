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

  /**
   * 设备拥有者的用户ID，如果设备属于开发者本身，则滞空
   */
  private String ownerId;

  /**
   * 冗余数据，用于做统计、报表、管理等.
   */
  @NotNull
  private String developerId;
}
