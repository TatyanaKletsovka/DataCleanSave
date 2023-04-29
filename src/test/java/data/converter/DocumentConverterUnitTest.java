package data.converter;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.dto.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class DocumentConverterUnitTest {
    @InjectMocks
    private DataConverter dataConverter;
    private Document document = new Document();
    private DocumentDto documentDto = new DocumentDto();
    private User user = new User();
    private Role role = new Role(3L, RoleName.USER);
    @BeforeEach
    public void setup() {
        Long id = 123456789L;
        String firstName = "John";
        String lastName = "Snow";
        String email = "john@email.com";
        LocalDateTime dateTime = LocalDateTime.now();
        boolean enabled = false;

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        user.setCreatedAt(dateTime);
        user.setEnabled(enabled);

        document.setId(id);
        document.setDateTime(dateTime);
        document.setUser(user);
        document.setProcessedRows(3);

        documentDto.setId(id);
        documentDto.setDateTime(dateTime);
        documentDto.setUserId(id);
        documentDto.setProcessedRows(3);
    }
        @Test
        public void should_SuccessfullyConvertDocumentToDocumentDto() {
            assertThat(dataConverter.convertToDocumentDto(document))
                    .usingRecursiveComparison()
                    .isEqualTo(documentDto);
        }
}
