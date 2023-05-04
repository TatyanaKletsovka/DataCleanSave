package data.converter;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.enums.WeekDay;
import com.syberry.poc.user.database.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PedestrianBicyclistConverterUnitTest {
    @InjectMocks
    private DataConverter dataConverter;
    private final Map<String, String> pedestrianBicyclistDataRecord = new HashMap<>();
    private final List<Map<String, String>> pedestrianBicyclistDataRecords = new ArrayList<>();
    private final List<PedestrianBicyclist> pedestrianBicyclistDataEntities = new ArrayList<>();
    @Mock
    private PedestrianBicyclistValues pedestrianBicyclistValues1;
    @Mock
    private PedestrianBicyclistValues pedestrianBicyclistValues2;
    private final List<PedestrianBicyclistValues> values = new ArrayList<>();
    private final PedestrianBicyclist pedestrianBicyclist = new PedestrianBicyclist();
    private final PedestrianBicyclist pedestrianBicyclistEntity = new PedestrianBicyclist();
    private final PedestrianBicyclistDto pedestrianBicyclistDto = new PedestrianBicyclistDto();
    private final Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );
    @BeforeEach
    public void setup() {
        Long id = 123456789L;
        int year = 2023;
        int month = 4;
        int day = 27;
        String date = "Thu, Apr 27, 2023";

        values.add(pedestrianBicyclistValues1);
        values.add(pedestrianBicyclistValues2);

        pedestrianBicyclist.setId(id);
        pedestrianBicyclist.setYear(year);
        pedestrianBicyclist.setMonth(month);
        pedestrianBicyclist.setDay(day);
        pedestrianBicyclist.setWeekDay(WeekDay.FRIDAY);
        pedestrianBicyclist.setDocument(document);
        pedestrianBicyclist.setValues(values);

        pedestrianBicyclistDto.setId(id);
        pedestrianBicyclistDto.setYear(year);
        pedestrianBicyclistDto.setMonth(month);
        pedestrianBicyclistDto.setDay(day);
        pedestrianBicyclistDto.setWeekDay(String.valueOf(WeekDay.FRIDAY));
        pedestrianBicyclistDto.setDocumentId(123L);

        pedestrianBicyclistDataRecord.put("date", date);

        pedestrianBicyclistEntity.setId(id);
        pedestrianBicyclistEntity.setYear(year);
        pedestrianBicyclistEntity.setMonth(month);
        pedestrianBicyclistEntity.setDay(day);
        pedestrianBicyclistEntity.setWeekDay(WeekDay.FRIDAY);
        pedestrianBicyclistEntity.setDocument(document);
        pedestrianBicyclistEntity.setValues(null);

        pedestrianBicyclistDataRecords.add(pedestrianBicyclistDataRecord);
        pedestrianBicyclistDataEntities.add(pedestrianBicyclistEntity);
    }
    @Test
    public void should_SuccessfullyConvertPedestrianBicyclistToPedestrianBicyclistDto() {
        assertThat(dataConverter.convertToPedestrianBicyclistDto(pedestrianBicyclist))
                .usingRecursiveComparison()
                .ignoringFields("listValues","values")
                .isEqualTo(pedestrianBicyclistDto);
    }
    @Test
    public void should_SuccessfullyGetPedestrianBicyclistValuesDtoFromPedestrianBicyclist() {
        assertThat(dataConverter.convertToPedestrianBicyclistDto(pedestrianBicyclist))
                .usingRecursiveComparison()
                .ignoringFields("listValues","values")
                .isEqualTo(pedestrianBicyclistDto);
    }

    @Test
    public void should_SuccessfullyConvertDocumentDataToPedestrianBicyclistEntities() {
        assertThat(dataConverter.convertToPedestrianBicyclistList(pedestrianBicyclistDataRecords, document))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(pedestrianBicyclistDataEntities);
    }
}
