package com.umasuo.device.center.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * exception body.
 * return customized code and message to the client.
 */
@Getter
@Setter
public class ExceptionBody {

  public static final int DEVICE_ALREADY_BOUND_CODE = 10004;
  public static final String DEVICE_ALREADY_BOUND_MESSAGE = "device already bound";

  /**
   * CODE.
   */
  private int code;

  /**
   * Message
   */
  private String message;

  public static ExceptionBody of(int code, String message) {
    ExceptionBody body = new ExceptionBody();
    body.code = code;
    body.message = message;
    return body;
  }
}
