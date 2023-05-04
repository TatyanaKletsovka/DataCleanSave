package com.syberry.poc.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown to indicate that an access token refresh operation failed.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 8L;

  public TokenRefreshException(String message) {
    super(String.format(message));
  }
}
