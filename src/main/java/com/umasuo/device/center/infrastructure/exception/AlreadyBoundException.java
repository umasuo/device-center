package com.umasuo.device.center.infrastructure.exception;

/**
 * Device already bound exception.
 */
public class AlreadyBoundException extends RuntimeException {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = -779392415326525211L;

  /**
   * Default constructor.
   */
  public AlreadyBoundException() {
    super();
  }

  /**
   * Constructor with message.
   *
   * @param message
   */
  public AlreadyBoundException(String message) {
    super(message);
  }
}
