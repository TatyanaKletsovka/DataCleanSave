package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that an invalid argument type was provided for a method or operation.
 */
public class InvalidArgumentTypeException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2L;

  public InvalidArgumentTypeException(String message) {
    super(message);
  }
}
