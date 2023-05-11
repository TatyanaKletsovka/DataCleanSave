package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that a date processing error occurred while processing the value.
 */
public class DateProcessingException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 9L;

  public DateProcessingException(String message) {
    super(message);
  }
}
