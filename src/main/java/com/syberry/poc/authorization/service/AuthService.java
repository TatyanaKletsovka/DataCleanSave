package com.syberry.poc.authorization.service;

import com.syberry.poc.authorization.dto.CreatePasswordDto;
import com.syberry.poc.authorization.dto.LoginDto;
import com.syberry.poc.authorization.dto.LoginRequestDto;
import javax.servlet.http.HttpServletRequest;

/**
 * User authorization interface.
 */
public interface AuthService {

  /**
   * Allows the user to log into application.
   *
   * @param dto login details
   * @return tokens and logged in user
   */
  LoginDto login(LoginRequestDto dto);

  /**
   * Extends duration of the token.
   *
   * @param request http request
   * @return refreshed tokens
   */
  LoginDto refreshToken(HttpServletRequest request);

  /**
   * Clear login information in system.
   *
   * @return cleared tokens
   */
  LoginDto logout();

  /**
   * Sends a reset password link to the user's email.
   *
   * @param email email for sending a reset password link
   */
  void resetPassword(String email);

  /**
   * Updates the user's password after validating the password reset token.
   *
   * @param dto CreatePasswordDto
   */
  void createNewPassword(CreatePasswordDto dto);
}
