package com.umasuo.device.center.infrastructure.exception;

/**
 * exception body.
 * return customized code and message to the client.
 */
public class ExceptionBody {

  /**
   * Device already bound code.
   */
  public static final int DEVICE_ALREADY_BOUND_CODE = 10004;

  /**
   * Device already bound message.
   */
  public static final String DEVICE_ALREADY_BOUND_MESSAGE = "device already bound";

  /**
   * CODE.
   */
  private int code;

  /**
   * Message
   */
  private String message;

  /**
   * Builder.
   *
   * @param code
   * @param message
   * @return
   */
  public static ExceptionBody of(int code, String message) {
    ExceptionBody body = new ExceptionBody();
    body.code = code;
    body.message = message;
    return body;
  }

  /**
   * Getter.
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
