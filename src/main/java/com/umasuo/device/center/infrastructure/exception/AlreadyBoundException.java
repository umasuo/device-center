package com.umasuo.device.center.infrastructure.exception;

/**
 * Created by Davis on 17/6/27.
 */
public class AlreadyBoundException extends RuntimeException{

  public AlreadyBoundException() {
  }

  public AlreadyBoundException(String message) {
    super(message);
  }
}
