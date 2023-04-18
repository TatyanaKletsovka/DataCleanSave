package user.converter;

import com.syberry.poc.exception.InvalidArgumentTypeException;
import com.syberry.poc.user.converter.RoleConverter;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.repository.RoleRepository;
import com.syberry.poc.user.dto.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoleConverterUnitTest {

  @InjectMocks
  private RoleConverter converter;
  @Mock
  private RoleRepository repository;

  private final Role role = new Role(3L, RoleName.USER);

  @Test
  public void should_SuccessfullyConvertToRoleFromEnum() {
    when(repository.findByRoleNameIfExists(any())).thenReturn(role);
    assertEquals(converter.convertToEntity(RoleName.USER), role);
  }

  @Test
  public void should_SuccessfullyConvertToRoleFromString() {
    when(repository.findByRoleNameIfExists(any())).thenReturn(role);
    assertEquals(converter.convertToEntity("user"), role);
  }

  @Test
  public void should_ThrowError_When_ConvertingInvalidString() {
    assertThrows(InvalidArgumentTypeException.class, () -> converter.convertToEntity(""));
  }
}
