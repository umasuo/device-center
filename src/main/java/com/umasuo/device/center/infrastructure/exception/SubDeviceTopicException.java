package com.umasuo.device.center.infrastructure.exception;

/**
 * Sub Device topic exception.
 */
public class SubDeviceTopicException extends RuntimeException {

  /**
   * Auto serial version id.
   */
  private static final long serialVersionUID = -9002228638348333157L;

  /**
   * Default constructor.
   */
  public SubDeviceTopicException() {
    super();
  }

  /**
   * Sub device topic exception.
   *
   * @param message
   */
  public SubDeviceTopicException(String message) {
    super(message);
  }
}
