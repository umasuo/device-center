package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/7/4.
 */
@Data
public class UnionDeviceView implements Serializable{

  private static final long serialVersionUID = 2183117062182600998L;

  private String unionId;

  protected Long createdAt;

  /**
   * The developer id.
   */
  private String developerId;

  private String productId;

  /**
   * 设备出产时带有的一个用于加密数据的key.
   */
  private String secretKey;
}
