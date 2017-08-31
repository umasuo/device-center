package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Union Device view.
 */
@Data
public class UnionDeviceView implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 2183117062182600998L;

  /**
   * union id.
   */
  private String unionId;

  /**
   * 设备出产时带有的一个用于加密数据的key.
   */
  private String secretKey;
}
