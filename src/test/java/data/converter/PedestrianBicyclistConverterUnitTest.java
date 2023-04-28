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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PedestrianBicyclistConverterUnitTest {
    @InjectMocks
    private DataConverter dataConverter;
    @Mock
    private PedestrianBicyclistValues pedestrianBicyclistValues1;
    @Mock
    private PedestrianBicyclistValues pedestrianBicyclistValues2;
    private List<PedestrianBicyclistValues> values = new ArrayList<>();
    private PedestrianBicyclist pedestrianBicyclist = new PedestrianBicyclist();
    private PedestrianBicyclistDto pedestrianBicyclistDto = new PedestrianBicyclistDto();
    private Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );
    @BeforeEach
    public void setup() {
        Long id = 123456789L;
        int year = 2023;
        int month = 4;
        int day = 27;

        values.add(pedestrianBicyclistValues1);
        values.add(pedestrianBicyclistValues2);

        pedestrianBicyclist.setId(id);
        pedestrianBicyclist.setYear(year);
        pedestrianBicyclist.setMonth(month);
        pedestrianBicyclist.setDay(day);
        pedestrianBicyclist.setWeekDay(WeekDay.SUNDAY);
        pedestrianBicyclist.setDocument(document);
        pedestrianBicyclist.setValues(values);

        pedestrianBicyclistDto.setId(id);
        pedestrianBicyclistDto.setYear(year);
        pedestrianBicyclistDto.setMonth(month);
        pedestrianBicyclistDto.setDay(day);
        pedestrianBicyclistDto.setWeekDay(String.valueOf(WeekDay.SUNDAY));
        pedestrianBicyclistDto.setDocumentId(123L);
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
}
