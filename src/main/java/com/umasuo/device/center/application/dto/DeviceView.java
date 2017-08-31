package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.infrastructure.enums.DeviceStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * Device view.
 */
@Data
public class DeviceView implements Serializable {

  /**
   * Auto generated serial id.
   */
  private static final long serialVersionUID = 8800732225747388907L;

  /**
   * Device id.
   */
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
   * 从系统批量获取的ID，用于唯一标志该设备。
   */
  private String unionId;

  /**
   * 任何接入云平台的设备，都属于一个事先定义好的设备类型.
   */
  private String productId;

  /**
   * 开发者ID，任何接入云平台的设备，都属于一个固定的开发者.
   */
  private String developerId;

  /**
   * 设备拥有者的ID，这里是userId，而不是developerID，如果没有指定，那么这台设备的拥有者则属于开发者本身.
   */
  private String ownerId;

  /**
   * Public key.
   */
  private String publicKey;

  /**
   * Device id.
   */
  private String deviceId;

  /**
   * Device status.
   */
  private DeviceStatus status;
}
