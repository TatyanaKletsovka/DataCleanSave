package com.syberry.poc.authorization.service.impl;

import com.google.common.cache.LoadingCache;
import com.syberry.poc.authorization.database.entity.RefreshToken;
import com.syberry.poc.authorization.dto.CreatePasswordDto;
import com.syberry.poc.authorization.dto.LoginDto;
import com.syberry.poc.authorization.dto.LoginRequestDto;
import com.syberry.poc.authorization.security.UserDetailsImpl;
import com.syberry.poc.authorization.service.AuthService;
import com.syberry.poc.authorization.service.EmailService;
import com.syberry.poc.authorization.service.RefreshTokenService;
import com.syberry.poc.authorization.util.SecurityUtils;
import com.syberry.poc.exception.EmailException;
import com.syberry.poc.exception.TokenRefreshException;
import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.converter.UserConverter;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.UserDto;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of AuthService interface.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;
  private final UserRepository userRepository;
  private final UserConverter userConverter;
  private final SecurityUtils securityUtils;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final LoadingCache<String, String> passwordResetTokenCache;

  /**
   * Allows the user to log into application.
   *
   * @param dto login details
   * @return tokens and logged in user
   */
  @Override
  public LoginDto login(LoginRequestDto dto) {
    UserDto userDto = findUserByEmail(dto.getEmail());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.getEmail(),
            dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authenticateUser((UserDetailsImpl) authentication.getPrincipal(), userDto);
  }

  /**
   * Extends duration of the token.
   *
   * @param request http request
   * @return refreshed tokens
   */
  @Override
  public LoginDto refreshToken(HttpServletRequest request) {
    String refreshToken = securityUtils.getJwtRefreshFromCookies(request);
    if (refreshToken != null && !refreshToken.isEmpty()) {
      return refreshTokenService.refreshAccessToken(refreshToken);
    } else {
      throw new TokenRefreshException("Refresh token is empty");
    }
  }

  /**
   * Clear login information in system.
   *
   * @return cleared tokens
   */
  @Override
  public LoginDto logout() {
    Long userId = SecurityUtils.getUserDetails().getId();
    refreshTokenService.deleteByUserId(userId);
    ResponseCookie accessCookie = securityUtils.getCleanJwtCookie();
    ResponseCookie refreshCookie = securityUtils.getCleanJwtRefreshCookie();
    return LoginDto.builder()
        .cookie(accessCookie.toString())
        .refreshCookie(refreshCookie.toString())
        .build();
  }

  /**
   * Sends a reset password link to the active user's email.
   *
   * @param email email for sending a reset password link
   */
  @Override
  public void resetPassword(String email) {
    User user = userRepository.findActiveUserByEmailIfExists(email);
    String token = UUID.randomUUID().toString();
    passwordResetTokenCache.put(user.getEmail(), token);
    emailService.sendEmail(token, user.getEmail());
  }

  /**
   * Updates the user's password after validating the password reset token.
   *
   * @param dto CreatePasswordDto
   */
  @Override
  @Transactional
  public void createNewPassword(CreatePasswordDto dto) {
    String email = dto.getEmail();
    if (retrieveToken(email).equals(dto.getToken())) {
      validatePassword(dto);
      passwordResetTokenCache.refresh(email);
      User user = userRepository.findActiveUserByEmailIfExists(email);
      user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
      user.setUpdatedAt(LocalDateTime.now());
    } else {
      throw new ValidationException("Password reset token is invalid");
    }
  }

  /**
   * Retrieve token or throw EmailException.
   *
   * @param email to check token
   * @return cookies with tokens
   * @throws EmailException if it is no token for presented email
   */
  private String retrieveToken(String email) {
    String token;
    try {
      token = passwordResetTokenCache.get(email);
    } catch (ExecutionException e) {
      throw new EmailException(e, "Email verification failed");
    }
    return token;
  }

  /**
   * Authenticate user.
   *
   * @param userDetails user's details data
   * @return cookies with tokens
   */
  private LoginDto authenticateUser(UserDetailsImpl userDetails, UserDto userDto) {
    ResponseCookie responseCookie = securityUtils.generateJwtCookie(userDetails);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    ResponseCookie refreshJwtCookie = securityUtils.generateRefreshJwtCookie(
        refreshToken.getToken());
    return LoginDto.builder()
        .cookie(responseCookie.toString())
        .refreshCookie(refreshJwtCookie.toString())
        .userDto(userDto)
        .build();
  }

  /**
   * Sends a request to get the active user by name to user repository.
   *
   * @param email email
   * @return userDto if user exist in repository
   */
  private UserDto findUserByEmail(String email) {
    return userConverter.convertToUserDto(
        userRepository.findActiveUserByEmailIfExists(email));
  }

  /**
   * Validates if passwords are equals.
   *
   * @param dto passwords to validate
   * @throws ValidationException if passwords are not equals
   */
  public void validatePassword(CreatePasswordDto dto) {
    if (!Objects.equals(dto.getNewPassword(), dto.getRepeatPassword())) {
      throw new ValidationException("Passwords don't match");
    }
  }
}
