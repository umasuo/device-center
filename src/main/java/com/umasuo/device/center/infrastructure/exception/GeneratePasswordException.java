package com.umasuo.device.center.infrastructure.exception;

/**
 * Created by Davis on 17/6/27.
 */
public class GeneratePasswordException extends RuntimeException{

  private static final long serialVersionUID = -9002228638348333157L;

  public GeneratePasswordException() {
  }

  public GeneratePasswordException(String message) {
    super(message);
  }
}
