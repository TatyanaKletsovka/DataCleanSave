package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that a validation error occurred while processing a request.
 */
public class ValidationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 4L;

  public ValidationException(String message) {
    super(message);
  }
}
