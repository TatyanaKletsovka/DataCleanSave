package com.syberry.poc.authorization.service;

import com.syberry.poc.authorization.database.entity.RefreshToken;
import com.syberry.poc.authorization.dto.LoginDto;

/**
 * Token management service.
 */
public interface RefreshTokenService {

  /**
   * Refreshes user's access tokens.
   *
   * @param accessToken user's access token
   * @return refreshed tokens
   */
  LoginDto refreshAccessToken(String accessToken);

  /**
   * Creates refresh token.
   *
   * @param userId user's id
   * @return refresh token
   */
  RefreshToken createRefreshToken(Long userId);

  /**
   * Checks if the token has expired.
   *
   * @param token user's token
   * @return refresh token
   */
  RefreshToken verifyExpiration(String token);

  /**
   * Deletes refresh token from repository.
   *
   * @param userId user's id
   */
  void deleteByUserId(Long userId);
}
