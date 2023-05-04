package data.service;

import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.impl.DataMapperServiceImpl;
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

    documentRow.put("county", county);
    documentRow.put("community", community);
    documentRow.put("on", onRoad);
    documentRow.put("from", fromRoad);
    documentRow.put("to", toRoad);
    documentRow.put("at", at);
    documentRow.put("approach", approach);
    documentRow.put("directions", directions);
    documentRow.put("dir", direction);
    documentRow.put("latitude", Float.toString(latitude));
    documentRow.put("longitude", Float.toString(longitude));

    documentData.add(documentRow);

    Tags.put("county", List.of(Tag.STRING));
    Tags.put("community", List.of(Tag.STRING));
    Tags.put("on", List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put("from", List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put("to", List.of(Tag.STRING, Tag.OBLIGATORY));
    Tags.put("at", List.of(Tag.STRING));
    Tags.put("approach", List.of(Tag.STRING));
    Tags.put("directions", List.of(Tag.STRING));
    Tags.put("dir", List.of(Tag.STRING));
    Tags.put("latitude", List.of(Tag.FLOAT));
    Tags.put("longitude", List.of(Tag.FLOAT));
  }

  @Test
  public void should_SuccessfullyMapData() {
    assertEquals(dataMapperService.mapColumns(documentData), Tags);
  }
}
