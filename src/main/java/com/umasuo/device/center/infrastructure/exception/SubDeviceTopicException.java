package com.umasuo.device.center.infrastructure.exception;

/**
 * Created by Davis on 17/6/27.
 */
public class SubDeviceTopicException extends RuntimeException{

  private static final long serialVersionUID = -9002228638348333157L;

  public SubDeviceTopicException() {
  }

  public SubDeviceTopicException(String message) {
    super(message);
  }
}
