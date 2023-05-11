package com.syberry.poc.authorization.controller;

import com.syberry.poc.authorization.dto.CreatePasswordDto;
import com.syberry.poc.authorization.dto.LoginDto;
import com.syberry.poc.authorization.dto.LoginRequestDto;
import com.syberry.poc.authorization.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for handling authentication and authorization-related HTTP requests.
 */
@RestController
@CrossOrigin
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  /**
   * Handles the user's authentication request.
   *
   * @param dto login details
   * @return cookies and logged-in user
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto) {
    log.info("POST-request: log in");
    return createHeader(authService.login(dto));
  }

  /**
   * Handles the user's log out request.
   *
   * @return cleared tokens
   */
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> logout() {
    log.info("POST-request: log out");
    return createHeader(authService.logout());
  }

  /**
   * Handles the user's refresh token request.
   *
   * @param httpServletRequest http request data
   * @return refreshed tokens
   */
  @PostMapping("/refresh")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest) {
    log.info("POST-request: refresh token");
    return createHeader(authService.refreshToken(httpServletRequest));
  }

  /**
   * Handles the user's reset password request.
   *
   * @param email email for sending a reset password link
   */
  @PostMapping("/reset")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resetPassword(@RequestBody String email) {
    log.info("POST-request: reset password");
    authService.resetPassword(email);
  }

  /**
   * Handles the user's request to create a new password.
   *
   * @param dto an object containing the needed information to set a new password
   */
  @PostMapping("/create-password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createNewPassword(@Valid @RequestBody CreatePasswordDto dto) {
    log.info("POST-request: create password");
    authService.createNewPassword(dto);
  }

  /**
   * Creates http header for response.
   *
   * @param loginDto login details
   * @return created http header
   */
  private ResponseEntity<?> createHeader(LoginDto loginDto) {
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, loginDto.getCookie())
        .header(HttpHeaders.SET_COOKIE, loginDto.getRefreshCookie())
        .body(loginDto.getUserDto());
  }
}
