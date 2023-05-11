package data.converter;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.dto.TrafficDto;
import com.syberry.poc.data.dto.enums.Direction;
import com.syberry.poc.user.database.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TrafficConverterUnitTest {
    @InjectMocks
    private DataConverter dataConverter;
    private final Map<String, String> trafficDataRecord = new HashMap<>();
    private final List<Map<String, String>> trafficDataRecords = new ArrayList<>();
    private final List<Traffic> trafficDataEntities = new ArrayList<>();
    private final Traffic traffic = new Traffic();
    private final TrafficDto trafficDto = new TrafficDto();
    private final Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );
    @BeforeEach
    public void setup() {
        Long id = 123456789L;
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

        traffic.setId(id);
        traffic.setCounty(county);
        traffic.setCommunity(community);
        traffic.setOnRoad(onRoad);
        traffic.setFromRoad(fromRoad);
        traffic.setToRoad(toRoad);
        traffic.setApproach(approach);
        traffic.setAt(at);
        traffic.setDirections(directions);
        traffic.setDirection(Direction.ONE_WAY);
        traffic.setLatitude(latitude);
        traffic.setLongitude(longitude);
        traffic.setDocument(document);

        trafficDto.setId(id);
        trafficDto.setCounty(county);
        trafficDto.setCommunity(community);
        trafficDto.setOnRoad(onRoad);
        trafficDto.setFromRoad(fromRoad);
        trafficDto.setToRoad(toRoad);
        trafficDto.setApproach(approach);
        trafficDto.setAt(at);
        trafficDto.setDirections(directions);
        trafficDto.setDirection(String.valueOf(Direction.ONE_WAY));
        trafficDto.setLatitude(latitude);
        trafficDto.setLongitude(longitude);
        trafficDto.setDocumentId(123L);

        trafficDataRecord.put("county", county);
        trafficDataRecord.put("community", community);
        trafficDataRecord.put("on", onRoad);
        trafficDataRecord.put("from", fromRoad);
        trafficDataRecord.put("to", toRoad);
        trafficDataRecord.put("at", at);
        trafficDataRecord.put("approach", approach);
        trafficDataRecord.put("directions", directions);
        trafficDataRecord.put("dir", direction);
        trafficDataRecord.put("latitude", Float.toString(latitude));
        trafficDataRecord.put("longitude", Float.toString(longitude));

        trafficDataRecords.add(trafficDataRecord);
        trafficDataEntities.add(traffic);
    }
        @Test
        public void should_SuccessfullyConvertCrashDataToCrashDataDto() {
            assertThat(dataConverter.convertToTrafficDto(traffic))
                    .usingRecursiveComparison()
                    .ignoringFields("countyHash", "communityHash",
                            "fromRoadHash", "directionsHash", "toRoadHash","onRoadHash","fromRoadHash",
                            "approachHash","approachHash","atHash")
                    .isEqualTo(trafficDto);
        }

        @Test
        public void should_SuccessfullyConvertDocumentDataToTrafficEntities() {
            assertThat(dataConverter.convertToTrafficList(trafficDataRecords, document))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(trafficDataEntities);
        }
}
