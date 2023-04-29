package data.converter;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.enums.InjuryType;
import com.syberry.poc.data.dto.enums.Weekend;
import com.syberry.poc.user.database.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class CrashDataConverterUnitTest {
    @InjectMocks
    private DataConverter dataConverter;
    private CrashData crashData = new CrashData();
    private CrashDataDto crashDataDto = new CrashDataDto();
    private Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );
    @BeforeEach
    public void setUp() {
        Long id = 123456789L;
        int year = 2023;
        int month = 4;
        int day = 27;
        int hour = 14;
        String collisionType = "Rear-end";
        String primaryFactor = "Distracted driving";
        String reportedLocation = "123 Main Street";
        Float latitude = 37.7749f;
        Float longitude = -122.4194f;

        crashData.setId(id);
        crashData.setYear(year);
        crashData.setMonth(month);
        crashData.setDay(day);
        crashData.setHour(hour);
        crashData.setWeekend(Weekend.WEEKEND);
        crashData.setCollisionType(collisionType);
        crashData.setInjuryType(InjuryType.FATAL);
        crashData.setPrimaryFactor(primaryFactor);
        crashData.setReportedLocation(reportedLocation);
        crashData.setLatitude(latitude);
        crashData.setLongitude(longitude);
        crashData.setDocument(document);

        crashDataDto.setId(id);
        crashDataDto.setYear(year);
        crashDataDto.setMonth(month);
        crashDataDto.setDay(day);
        crashDataDto.setHour(hour);
        crashDataDto.setWeekend(String.valueOf(Weekend.WEEKEND));
        crashDataDto.setCollisionType(collisionType);
        crashDataDto.setInjuryType(String.valueOf(InjuryType.FATAL));
        crashDataDto.setPrimaryFactor(primaryFactor);
        crashDataDto.setReportedLocation(reportedLocation);
        crashDataDto.setLatitude(latitude);
        crashDataDto.setLongitude(longitude);
        crashDataDto.setDocumentId(123L);
    }
        @Test
        public void should_SuccessfullyConvertCrashDataToCrashDataDto() {
            assertThat(dataConverter.convertToCrashDataDto(crashData))
                    .usingRecursiveComparison()
                    .ignoringFieldsOfTypes()
                    .isEqualTo(crashDataDto);
        }
}
