package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/7/14.
 * 用户的消息格式.
 */
@Data
public class UserMessage implements Serializable {

  private static final long serialVersionUID = 9190245794314412899L;

  /**
   * 用户ID，非空
   */
  private String userId;

  /**
   * 消息时间.
   */
  private long t;

  /**
   * 设备ID，可空.
   */
  private String deviceId;

  private Content content;

  @Data
  class Content implements Serializable {

    private static final long serialVersionUID = -7158942951264289765L;
    /**
     * 消息类型
     */
    private String id;

    /**
     * 具体数据，json字符串或者具体数值.
     */
    private String data;
  }

}
