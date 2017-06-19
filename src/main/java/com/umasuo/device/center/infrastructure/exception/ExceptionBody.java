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

  public static final int DEVELOPER_NOT_EXIST_CODE = 10001;
  public static final String DEVELOPER_NOT_EXIST_MESSAGE = "developer not exist.";

  public static final int DEVELOPER_ALREADY_EXIST_CODE = 10002;
  public static final String DEVELOPER_ALREADY_EXIST_MESSAGE = "developer already exist.";

  public static final int EMAIL_OR_PASSWORD_ERROR_CODE = 10003;
  public static final String EMAIL_OR_PASSWORD_ERROR_MESSAGE = "email or password not correct.";

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
