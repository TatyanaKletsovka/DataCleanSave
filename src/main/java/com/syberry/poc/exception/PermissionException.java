package com.syberry.poc.exception;

import java.io.Serial;

/**
 * Thrown to indicate that a user tries to perform an action for which they do not have permission.
 */
public class PermissionException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 3L;

  public PermissionException(String message) {
    super(message);
  }
}
