package data.converter;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.database.repository.PedestrianBicyclistRepository;
import com.syberry.poc.data.dto.enums.WeekDay;
import com.syberry.poc.user.database.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PedestrianBicyclistValuesConverterUnitTest {
  @InjectMocks
  private DataConverter dataConverter;
  @Mock
  private PedestrianBicyclistRepository pedestrianBicyclistRepository;
  private final List<Map<String, String>> pedestrianBicyclistValueRecords = new ArrayList<>();
  private final List<PedestrianBicyclistValues> pedestrianBicyclistValueEntities = new ArrayList<>();
  private final Map<String, String> pedestrianBicyclistValueRecord = new HashMap<>();
  @Mock
  private PedestrianBicyclistValues pedestrianBicyclistValue = new PedestrianBicyclistValues();
  private final PedestrianBicyclist pedestrianBicyclistEntity = new PedestrianBicyclist();
  private final Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );

  @BeforeEach
  public void setup() {
    String columnName = "Address";
    String columnValue = "250";
    String date = "Fri, Apr 27, 2023";
    Long id = 123456789L;
    int year = 2023;
    int month = 4;
    int day = 27;

    pedestrianBicyclistEntity.setId(id);
    pedestrianBicyclistEntity.setYear(year);
    pedestrianBicyclistEntity.setMonth(month);
    pedestrianBicyclistEntity.setDay(day);
    pedestrianBicyclistEntity.setWeekDay(WeekDay.FRIDAY);
    pedestrianBicyclistEntity.setDocument(document);
    pedestrianBicyclistEntity.setValues(null);

    pedestrianBicyclistValue.setColumnName(columnName);
    pedestrianBicyclistValue.setValue(columnValue);
    pedestrianBicyclistValue.setPedestrianBicyclist(null);

    pedestrianBicyclistValueRecord.put("date", date);

    pedestrianBicyclistValueRecords.add(pedestrianBicyclistValueRecord);
    pedestrianBicyclistValueEntities.add(pedestrianBicyclistValue);
  }
  @Test
  public void should_SuccessfullyConvertDocumentDataToPedestrianBicyclistEntities() {
    Mockito.when(pedestrianBicyclistRepository
        .findTopByYearAndMonthAndDayAndWeekDayOrderByIdDesc(2023, 4, 27, WeekDay.FRIDAY))
      .thenReturn(Optional.of(pedestrianBicyclistEntity));

    assertThat(dataConverter.convertToPedestrianBicyclistValuesList(pedestrianBicyclistValueRecords))
        .usingRecursiveComparison()
        .ignoringFields("id", "valueHash", "columnNameHash", "pedestrianBicyclist")
        .isEqualTo(pedestrianBicyclistValueEntities);
  }
}
