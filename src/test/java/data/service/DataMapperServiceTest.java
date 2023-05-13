package data.service;

import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.impl.DataMapperServiceImpl;
import com.syberry.poc.data.util.ColumnNameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataMapperServiceTest {
  @InjectMocks
  private DataMapperServiceImpl dataMapperService;
  private final List<Map<String, String>> documentData = new ArrayList<>();
  private final Map<String, String> documentRow = new HashMap<>();
  private final Map<String, List<Tag>> Tags = new HashMap<>();

  @BeforeEach
  public void setup() {
    Float latitude = 37.7749f;
    Float longitude = -122.4194f;
    String county = "county";
    String community = "communityValue";
    String onRoad = "onRoad";
    String fromRoad = "Main Street";
    String toRoad = "Oak Avenue";
    String approach = "North";
    String at = "Intersection";
    String directions = "Northbound";
    String direction = "1-way";

    documentRow.put(ColumnNameConstants.COUNTY, county);
    documentRow.put(ColumnNameConstants.COMMUNITY, community);
    documentRow.put(ColumnNameConstants.ON, onRoad);
    documentRow.put(ColumnNameConstants.FROM, fromRoad);
    documentRow.put(ColumnNameConstants.TO, toRoad);
    documentRow.put(ColumnNameConstants.AT, at);
    documentRow.put(ColumnNameConstants.APPROACH, approach);
    documentRow.put(ColumnNameConstants.DIRECTIONS, directions);
    documentRow.put(ColumnNameConstants.DIR, direction);
    documentRow.put(ColumnNameConstants.LATITUDE, Float.toString(latitude));
    documentRow.put(ColumnNameConstants.LONGITUDE, Float.toString(longitude));

    documentData.add(documentRow);

    Tags.put(ColumnNameConstants.COUNTY, List.of(Tag.STRING));
    Tags.put(ColumnNameConstants.COMMUNITY, List.of(Tag.STRING));
    Tags.put(ColumnNameConstants.ON, List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put(ColumnNameConstants.FROM, List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put(ColumnNameConstants.TO, List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put(ColumnNameConstants.AT, List.of(Tag.STRING));
    Tags.put(ColumnNameConstants.APPROACH, List.of(Tag.STRING));
    Tags.put(ColumnNameConstants.DIRECTIONS, List.of(Tag.STRING));
    Tags.put(ColumnNameConstants.DIR, List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put(ColumnNameConstants.LATITUDE, List.of(Tag.FLOAT));
    Tags.put(ColumnNameConstants.LONGITUDE, List.of(Tag.FLOAT));
  }

  @Test
  public void should_SuccessfullyMapData() {
    assertEquals(dataMapperService.mapColumns(documentData), Tags);
  }
}
