package com.syberry.poc.exception;

import java.io.Serial;
import java.io.Serializable;

/**
 * Thrown to indicate that an entity is not found.
 */
public class EntityNotFoundException extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(String message) {
    super(message);
  }
}
