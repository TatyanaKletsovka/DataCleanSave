package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that an error occurred while sending an email.
 */
public class EmailException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 7L;

  public EmailException(Throwable throwable, String message) {
    super(message, throwable);
  }
}
