package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that a date parsing error occurred while parsing from string.
 */
public class DateParseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 6L;

  public DateParseException(String message) {
    super(message);
  }
}
