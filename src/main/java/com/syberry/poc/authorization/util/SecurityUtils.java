package com.syberry.poc.authorization.util;

import com.syberry.poc.authorization.security.UserDetailsImpl;
import com.syberry.poc.user.database.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * Component for security module.
 */
@Slf4j
@Component
public class SecurityUtils {

  private static final String SECRET = "crew22";
  private static final String ACCESS_COOKIE_NAME = "accessToken";
  private static final String REFRESH_COOKIE_NAME = "refreshToken";
  private static final int ACCESS_TOKEN_MINUTES = 15;
  private static final int ACCESS_TOKEN_HOURS = 24;
  private static final int MINUTES_SECONDS = 60;
  private static final String PATH = "/";
  private static final long ACCESS_TOKEN_DURATION_SEC = ACCESS_TOKEN_MINUTES * MINUTES_SECONDS;
  private static final long REFRESH_TOKEN_DURATION_SEC = ACCESS_TOKEN_HOURS
      * MINUTES_SECONDS * MINUTES_SECONDS;

  /**
   * Generates cookie with access token.
   *
   * @param userDetails user's detail information
   * @return cookie with token
   */
  public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
    String jwt = generateTokenFromUsername(userDetails.getUsername());
    return generateCookie(ACCESS_COOKIE_NAME, jwt);
  }

  /**
   * Generates cookie with access token.
   *
   * @param user user entity
   * @return cookie with token
   */
  public ResponseCookie generateJwtCookie(User user) {
    String jwt = generateTokenFromUsername(user.getEmail());
    return generateCookie(ACCESS_COOKIE_NAME, jwt);
  }

  /**
   * Generates refresh cookie.
   *
   * @param refreshToken refresh token data
   * @return refresh cookie
   */
  public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
    return generateCookie(REFRESH_COOKIE_NAME, refreshToken);
  }

  /**
   * Gets access token from cookie.
   *
   * @param request http request
   * @return token's data
   */
  public String getJwtFromCookies(HttpServletRequest request) {
    return getCookieValueByName(request, ACCESS_COOKIE_NAME);
  }

  /**
   * Gets refresh token from cookie.
   *
   * @param request http request
   * @return token's data
   */
  public String getJwtRefreshFromCookies(HttpServletRequest request) {
    return getCookieValueByName(request, REFRESH_COOKIE_NAME);
  }

  /**
   * Gets cookie with empty access token.
   *
   * @return cookie with empty value
   */
  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(ACCESS_COOKIE_NAME, null).path(PATH).build();
  }

  /**
   * Gets cookie with empty refresh token.
   *
   * @return cookie with empty value
   */
  public ResponseCookie getCleanJwtRefreshCookie() {
    return ResponseCookie.from(REFRESH_COOKIE_NAME, null).path(PATH).build();
  }

  /**
   * Gets username from token.
   *
   * @param token token
   * @return username
   */
  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Validates token.
   *
   * @param token token's data
   * @return true if token is valid and false if not
   */
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException | MalformedJwtException | ExpiredJwtException
        | UnsupportedJwtException | IllegalArgumentException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    }
    return false;
  }

  /**
   * Generates access token from username.
   *
   * @param username username
   * @return token
   */
  public String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime()
            + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_MINUTES)))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  /**
   * Generates cookie.
   *
   * @param name cookie name
   * @param value payload
   * @return generated cookie
   */
  private static ResponseCookie generateCookie(String name, String value) {
    return ResponseCookie.from(name, value)
        .path(SecurityUtils.PATH)
        .maxAge(name.equals(
            ACCESS_COOKIE_NAME) ? ACCESS_TOKEN_DURATION_SEC : REFRESH_TOKEN_DURATION_SEC)
        .httpOnly(true)
        .secure(false)
        .build();
  }

  /**
   * Gets cookie's value by cookie name.
   *
   * @param request http request
   * @param name cookie name
   * @return payload
   */
  private static String getCookieValueByName(HttpServletRequest request, String name) {
    Cookie cookie = WebUtils.getCookie(request, name);
    return cookie != null ? cookie.getValue() : null;
  }

  /**
   * Gets user details from security context.
   *
   * @return user's detail data
   */
  public static UserDetailsImpl getUserDetails() {
    return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
