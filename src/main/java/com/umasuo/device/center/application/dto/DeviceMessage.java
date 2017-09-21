package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 目前为止，消息由APP直接处理，服务端不进行消息的处理.
 */
@Data
public class DeviceMessage implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 9190245794314412899L;

  /**
   * Message type.
   */
  private int type;

  /**
   * time.
   */
  private long t;

  /**
   * Device id.
   */
  private String deviceId;

  /**
   * Content.
   */
  private Content content;

  /**
   * Content.
   */
  @Data
  public static class Content implements Serializable {

    /**
     * Auto generated serial version id.
     */
    private static final long serialVersionUID = -7158942951264289765L;
    /**
     * 设备活着数据功能点ID.
     */
    private String id;

    /**
     * 具体数据，json字符串活着具体数值.
     */
    private String data;
  }

}
