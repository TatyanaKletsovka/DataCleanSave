package validation;

import com.syberry.poc.authorization.security.UserDetailsImpl;
import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.PasswordUpdatingDto;
import com.syberry.poc.user.dto.enums.RoleName;
import com.syberry.poc.user.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserValidatorUnitTest {

  @InjectMocks
  private UserValidator validator;
  @Mock
  private UserRepository repository;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private Authentication authentication;
  @Mock
  private PasswordEncoder passwordEncoder;

  private final String email = "example@gmail.com";
  private final String oldPassword = "password";
  private final String newPassword = "newPassword";
  private User user = new User();

  @BeforeEach
  public void mock() {
    user.setId(2L);
    user.setEmail(email);
    user.setPassword(oldPassword);

    when(passwordEncoder.matches(anyString(), anyString()))
        .thenReturn(true);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(1L, "super_admin@gmail.com",
        oldPassword, true,
        new SimpleGrantedAuthority(RoleName.ADMIN.name())));
  }

  @Test
  public void should_SuccessfullyValidateEmail_When_EmailIsUnique() {
    when(repository.existsByEmail(anyString())).thenReturn(false);
    assertDoesNotThrow(() -> validator.validateEmail(email));
  }

  @Test
  public void should_ThrowError_When_EmailIsNotUnique() {
    when(repository.existsByEmail(anyString())).thenReturn(true);
    assertThrows(ValidationException.class, () -> validator.validateEmail(email));
  }

  @Test
  public void should_SuccessfullyValidateUpdating() {
    user.setEnabled(true);
    assertDoesNotThrow(() -> validator.validateUpdating(user));
  }

  @Test
  public void should_ThrowError_When_UserDisabled() {
    user.setEnabled(false);
    assertThrows(ValidationException.class, () -> validator.validateUpdating(user));
  }

  @Test
  public void should_SuccessfullyValidateRole() {
    user.setRole(new Role(2L, RoleName.ADMIN));
    assertDoesNotThrow(() -> validator.validateRole(user));
  }

  @Test
  public void should_ThrowError_When_UserIsSuperAdmin() {
    user.setRole(new Role(1L, RoleName.SUPER_ADMIN));
    assertThrows(ValidationException.class, () -> validator.validateRole(user));
  }

  @Test
  public void should_SuccessfullyValidatePasswords() {
    validator.validatePassword(new PasswordUpdatingDto(oldPassword, newPassword));
  }

  @Test
  public void should_ThrowError_When_NewPasswordEqualsOldPassword() {
    assertThrows(ValidationException.class,
        () -> validator.validatePassword(new PasswordUpdatingDto(oldPassword, oldPassword)));
  }

  @Test
  public void should_ThrowError_When_NewPasswordNotEqualsUserPassword() {
    assertThrows(ValidationException.class,
        () -> validator.validatePassword(new PasswordUpdatingDto(newPassword, newPassword)));
  }
}
