package user.converter;

import com.syberry.poc.user.converter.RoleConverter;
import com.syberry.poc.user.converter.UserConverter;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserUpdatingDto;
import com.syberry.poc.user.dto.enums.RoleName;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserConverterUnitTest {

  @InjectMocks
  private UserConverter userConverter;
  @Mock
  private RoleConverter roleConverter;

  private User user = new User();
  private UserCreationDto creationDto = new UserCreationDto();
  private UserUpdatingDto updatingDto = new UserUpdatingDto();
  private UserDto userDto = new UserDto();
  private Role role = new Role(3L, RoleName.USER);

  @BeforeEach
  public void mock() {
    Long id = 2L;
    String firstName = "John";
    String lastName = "Snow";
    String email = "john@email.com";
    String roleName = "user";
    LocalDateTime createdAt = LocalDateTime.now();
    boolean enabled = true;

    user.setId(id);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setRole(role);
    user.setCreatedAt(createdAt);
    user.setEnabled(enabled);

    creationDto.setFirstName(firstName);
    creationDto.setLastName(lastName);
    creationDto.setEmail(email);
    creationDto.setRoleName(roleName);

    updatingDto.setFirstName(firstName);
    updatingDto.setLastName(lastName);

    userDto.setId(id);
    userDto.setFirstName(firstName);
    userDto.setLastName(lastName);
    userDto.setEmail(email);
    userDto.setRoleName(RoleName.USER);
    userDto.setCreatedAt(createdAt);
    userDto.setEnabled(enabled);
  }

  @Test
  public void should_SuccessfullyConvertUserCreationDtoToEntity() {
    when(roleConverter.convertToEntity(anyString())).thenReturn(role);
    assertThat(userConverter.convertToEntity(creationDto))
      .usingRecursiveComparison()
      .ignoringFields("id", "createdAt")
      .isEqualTo(user);
  }
  @Test
  public void should_SuccessfullyConvertUserUpdatingDtoToEntity() {
    assertThat(userConverter.convertToEntity(updatingDto, user))
      .usingRecursiveComparison()
      .ignoringFields("updatedAt")
      .isEqualTo(user);
  }

  @Test
  public void should_SuccessfullyConvertToUserDto() {
    assertThat(userConverter.convertToUserDto(user))
        .usingRecursiveComparison()
        .isEqualTo(userDto);
  }
}
