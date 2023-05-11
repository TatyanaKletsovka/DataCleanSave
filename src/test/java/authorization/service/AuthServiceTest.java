package authorization.service;

import com.google.common.cache.LoadingCache;
import com.syberry.poc.authorization.database.entity.RefreshToken;
import com.syberry.poc.authorization.dto.CreatePasswordDto;
import com.syberry.poc.authorization.dto.LoginDto;
import com.syberry.poc.authorization.dto.LoginRequestDto;
import com.syberry.poc.authorization.security.UserDetailsImpl;
import com.syberry.poc.authorization.service.EmailService;
import com.syberry.poc.authorization.service.RefreshTokenService;
import com.syberry.poc.authorization.service.impl.AuthServiceImpl;
import com.syberry.poc.authorization.util.SecurityUtils;
import com.syberry.poc.exception.EmailException;
import com.syberry.poc.exception.EntityNotFoundException;
import com.syberry.poc.exception.TokenRefreshException;
import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.converter.UserConverter;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.enums.RoleName;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {

  @InjectMocks
  private AuthServiceImpl authService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RefreshTokenService refreshTokenService;
  @Mock
  private EmailService emailService;
  @Mock
  private LoadingCache<String, String> passwordResetTokenCache;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private SecurityUtils securityUtils;
  @Mock
  private Authentication authentication;
  @Mock
  private UserConverter userConverter;
  @Mock
  private SecurityContext securityContext;
  private final Long id = 1L;
  private final String email = "super_admin@gmail.com";
  private final CreatePasswordDto dto = new CreatePasswordDto(email,
      "c5837db0-2530-4eaf-b45d-f796eb0ae589", "password", "password");

  @Test
  public void should_SuccessfullyLogin() {
    Role role = new Role(id, RoleName.SUPER_ADMIN);
    User user = User.builder()
        .id(id)
        .email(email)
        .password("password")
        .role(role)
        .build();
    UserDto userDto = UserDto.builder()
        .id(id)
        .email(email)
        .build();
    UserDetailsImpl userDetails = UserDetailsImpl.create(user);
    RefreshToken refreshToken = RefreshToken.builder()
        .id(id)
        .user(user)
        .token("refresh-token")
        .expiryDate(Instant.now().plus(1, ChronoUnit.DAYS))
        .build();
    ResponseCookie jwtCookie = ResponseCookie.from(
        "access-token", "access-token").build();
    ResponseCookie refreshJwtCookie = ResponseCookie.from(
        "refresh-token", "refresh-token").build();
    ResponseCookie accessCookie = ResponseCookie.from(
        "access-token", "access-token").build();
    LoginRequestDto loginRequestDto =
        new LoginRequestDto(email, "password");
    LoginDto loginDto = LoginDto.builder()
        .cookie(jwtCookie.toString())
        .refreshCookie(refreshJwtCookie.toString())
        .userDto(userDto)
        .build();

    when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
    when(userRepository.findActiveUserByEmailIfExists(anyString())).thenReturn(user);
    when(refreshTokenService.createRefreshToken(anyLong())).thenReturn(refreshToken);
    when(securityUtils.generateJwtCookie(userDetails)).thenReturn(accessCookie);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userConverter.convertToUserDto(any(User.class))).thenReturn(userDto);
    when(securityUtils.generateRefreshJwtCookie(anyString())).thenReturn(refreshJwtCookie);
    when(userRepository.findByIdIfExists(anyLong())).thenReturn(user);
    assertThat(authService.login(loginRequestDto)).isEqualTo(loginDto);
  }

  @Test
  public void should_ThrowError_WhenLoginWithNoneExistingUsername() {
    when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
    when(userRepository.findActiveUserByEmailIfExists(null))
        .thenThrow(EntityNotFoundException.class);
    assertThrows(EntityNotFoundException.class, () -> authService.login(new LoginRequestDto()));
  }

  @Test
  public void should_SuccessfullyRefreshToken() {
    String token = "refreshToken";
    when(securityUtils.getJwtRefreshFromCookies(any())).thenReturn(token);
    ResponseCookie jwtCookie = ResponseCookie.from("cookie", token).build();
    LoginDto loginDto = new LoginDto(jwtCookie.toString(), "refresh", null);
    when(refreshTokenService.refreshAccessToken(anyString())).thenReturn(loginDto);
    assertThat(authService.refreshToken(null)).isEqualTo(loginDto);
  }

  @Test
  public void should_ThrowError_When_RefreshingWithEmptyToken() {
    when(securityUtils.getJwtRefreshFromCookies(any(HttpServletRequest.class))).thenReturn("");
    assertThrows(TokenRefreshException.class, () -> authService.refreshToken(null));
  }

  @Test
  public void should_ThrowError_When_RefreshingWithExpiredToken() {
    String token = "token";
    when(securityUtils.getJwtRefreshFromCookies(any(HttpServletRequest.class))).thenReturn(token);
    when(refreshTokenService.refreshAccessToken(anyString()))
        .thenThrow(TokenRefreshException.class);
    assertThrows(TokenRefreshException.class, () -> authService.refreshToken(null));
  }

  @Test
  public void should_SuccessfullyLogout() {
    SecurityContextHolder.setContext(securityContext);
    UserDetailsImpl userDetails = new UserDetailsImpl(
            id, email, "password", true,
            new SimpleGrantedAuthority(RoleName.ADMIN.name()));
    ResponseCookie refreshCookie = ResponseCookie.from("refresh-token", null).build();
    ResponseCookie accessCookie = ResponseCookie.from("access-token", null).build();
    LoginDto loginDto = new LoginDto("access-token=", "refresh-token=", null);

    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(securityUtils.getCleanJwtCookie()).thenReturn(accessCookie);
    when(securityUtils.getCleanJwtRefreshCookie()).thenReturn(refreshCookie);
    assertThat(authService.logout()).isEqualTo(loginDto);
  }

  @Test
  void resetPasswordWhenUserExistsThenSendsEmail() {
    User user = new User();
    user.setEmail(email);
    when(userRepository.findActiveUserByEmailIfExists(email)).thenReturn(user);
    doNothing().when(emailService).sendEmail(anyString(), anyString());
    doNothing().when(passwordResetTokenCache).put(eq(email), anyString());

    authService.resetPassword(email);

    verify(emailService).sendEmail(anyString(), eq(email));
  }

  @Test
  void resetPasswordWhenUserIsNotFoundThenThrowsException() {
    when(userRepository.findActiveUserByEmailIfExists(anyString()))
        .thenThrow(EntityNotFoundException.class);
    assertThrows(EntityNotFoundException.class, () -> authService.resetPassword(anyString()));
  }

  @Test
  void restorePasswordWhenTokenForEmailIsNotFoundInCacheThenThrowsException() throws ExecutionException {
    when(passwordResetTokenCache.get(anyString())).thenThrow(ExecutionException.class);
    assertThrows(EmailException.class, () -> authService.createNewPassword(dto));
  }

  @Test
  void restorePasswordWhenTokenIsInvalidThenThrowsException() throws ExecutionException {
    when(passwordResetTokenCache.get(anyString())).thenReturn("");
    assertThrows(ValidationException.class, () -> authService.createNewPassword(dto));
  }

  @Test
  void restorePasswordWhenTokenIsCorrectThenUpdatesPassword() throws ExecutionException {
    String email = dto.getEmail();
    String newPassword = dto.getNewPassword();
    User user = new User();
    user.setEmail(email);
    user.setPassword(newPassword + "old");
    when(passwordResetTokenCache.get(anyString())).thenReturn(dto.getToken());
    when(userRepository.findActiveUserByEmailIfExists(email)).thenReturn(user);
    when(passwordEncoder.encode(anyString())).thenReturn(newPassword);
    authService.createNewPassword(dto);
    assertThat(user.getPassword()).isEqualTo(newPassword);
  }
}
