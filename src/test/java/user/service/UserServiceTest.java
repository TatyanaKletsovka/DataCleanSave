package user.service;

import com.syberry.poc.exception.EntityNotFoundException;
import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.converter.RoleConverter;
import com.syberry.poc.user.converter.UserConverter;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserFilter;
import com.syberry.poc.user.dto.UserUpdatingDto;
import com.syberry.poc.user.dto.enums.RoleName;
import com.syberry.poc.user.service.impl.UserServiceImpl;
import com.syberry.poc.user.specification.UserSpecification;
import com.syberry.poc.user.validation.UserValidator;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

  @InjectMocks
  private UserServiceImpl service;
  @Mock
  private UserConverter converter;
  @Mock
  private RoleConverter roleConverter;
  @Mock
  private UserRepository repository;
  @Mock
  private UserValidator validator;
  @Mock
  private UserSpecification specification;

  private User user = new User();
  private UserDto userDto = new UserDto();
  private UserCreationDto creationDto = new UserCreationDto();
  private UserUpdatingDto updatingDto = new UserUpdatingDto();
  private Role role = new Role(3L, RoleName.USER);

  @BeforeEach
  public void mock() {
    Long id = 2L;
    String firstName = "John";
    String lastName = "Snow";
    String email = "john@email.com";
    LocalDateTime createdAt = LocalDateTime.now();
    boolean enabled = false;

    user.setId(id);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setRole(role);
    user.setCreatedAt(createdAt);
    user.setEnabled(enabled);

    userDto.setId(id);
    userDto.setFirstName(firstName);
    userDto.setLastName(lastName);
    userDto.setEmail(email);
    userDto.setRoleName(RoleName.USER);
    userDto.setCreatedAt(createdAt);
    userDto.setEnabled(enabled);
  }

  @Test
  public void should_SuccessfullyReturnAllUsers() {
    when(repository.findAll(
        specification.buildGetAllSpecification(any(UserFilter.class)), PageRequest.of(0, 20)))
        .thenReturn(new PageImpl<>(List.of()));
    assertEquals(new PageImpl<>(List.of()),
        service.findAllUsers(any(UserFilter.class), PageRequest.of(0, 20)));
  }

  @Test
  public void should_SuccessfullyFindUserById() {
    when(repository.findByIdIfExists(anyLong())).thenReturn(user);
    when(converter.convertToUserDto(any(User.class))).thenReturn(userDto);
    assertEquals(userDto, service.findUserById(anyLong()));
  }

  @Test
  public void should_ThrowError_WhenFindingByIdNoneExistingUser() {
    when(repository.findByIdIfExists(-1L)).thenThrow(EntityNotFoundException.class);
    assertThrows(EntityNotFoundException.class, () -> service.findUserById(-1L));
  }

  @Test
  public void should_SuccessfullyCreateUser() {
    when(repository.existsByEmail(anyString())).thenReturn(false);
    when(converter.convertToEntity(any(UserCreationDto.class))).thenReturn(user);
    when(repository.save(any(User.class))).thenReturn(user);
    when(converter.convertToUserDto(any(User.class))).thenReturn(userDto);
    assertEquals(userDto, service.createUser(creationDto));
  }

  @Test
  public void should_ThrowError_When_CreatingUserWithExistingEmail() {
    when(converter.convertToEntity(any(UserCreationDto.class))).thenReturn(user);
    when(repository.existsByEmail(anyString())).thenReturn(true);
    doThrow(ValidationException.class).when(validator).validateEmail(any());
    assertThrows(ValidationException.class, () -> service.createUser(creationDto));
  }

  @Test
  public void should_SuccessfullyUpdateUserById() {
    when(repository.findByIdIfExists(anyLong())).thenReturn(user);
    when(converter.convertToUserDto(any())).thenReturn(userDto);
    assertEquals(userDto, service.updateUserById(updatingDto));
  }

  @Test
  public void should_ThrowError_When_UpdatingDisabledUser() {
    doThrow(ValidationException.class).when(validator).validateUpdating(any());
    assertThrows(ValidationException.class, () -> service.updateUserById(updatingDto));
  }

  @Test
  public void should_SuccessfullyDisableUser() {
    userDto.setEnabled(false);
    when(repository.findByIdIfExists(any())).thenReturn(user);
    when(converter.convertToUserDto(any())).thenReturn(userDto);
    assertEquals(userDto, service.disableUserById(2L));
  }

  @Test
  public void should_SuccessfullySwitchAdminRoleById() {
    user.setRole(role);
    when(repository.findByIdIfExists(any())).thenReturn(user);
    when(converter.convertToUserDto(any())).thenReturn(userDto);
    when(roleConverter.convertToEntity(any(RoleName.class))).thenReturn(role);
    assertEquals(userDto, service.switchAdminRoleById(2L));
  }
  @Test
  public void should_ThrowError_When_UpdatingSuperAdmin() {
    user.setRole(new Role(1L, RoleName.SUPER_ADMIN));
    when(repository.findByIdIfExists(any())).thenReturn(user);
    doThrow(ValidationException.class).when(validator).validateRole(any());
    assertThrows(ValidationException.class, () -> service.switchAdminRoleById(2L));
  }
}
