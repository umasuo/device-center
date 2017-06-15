package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/6/5.
 */
@Data
public class DeviceView implements Serializable {

  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 8800732225747388907L;

  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * device definition id.
   */
  private String deviceDefineId;

  /**
   * 开发者自定义的设备ID.
   */
  private String customizedId;

  private String ownerId;

  private String developerId;
}
