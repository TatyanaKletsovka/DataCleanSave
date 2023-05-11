package com.syberry.poc.exception;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown to indicate that a file reading error occurred while processing a request.
 */
public class FileReadingException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5L;

  public FileReadingException(String message, IOException originalException) {
    super(message + originalException.getMessage());
  }
}
