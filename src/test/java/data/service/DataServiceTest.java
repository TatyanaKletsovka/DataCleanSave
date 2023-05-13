package data.service;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.database.repository.CrashDataRepository;
import com.syberry.poc.data.database.repository.DocumentRepository;
import com.syberry.poc.data.database.repository.PedestrianBicyclistRepository;
import com.syberry.poc.data.database.repository.PedestrianBicyclistValuesRepository;
import com.syberry.poc.data.database.repository.TrafficRepository;
import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.CrashDataFilter;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.PedestrianBicyclistFilter;
import com.syberry.poc.data.dto.TrafficDto;
import com.syberry.poc.data.dto.TrafficFilter;
import com.syberry.poc.data.service.impl.DataServiceImpl;
import com.syberry.poc.data.specification.CrashDataSpecification;
import com.syberry.poc.data.specification.PedestrianBicyclistSpecification;
import com.syberry.poc.data.specification.TrafficSpecification;
import com.syberry.poc.user.database.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataServiceTest {
    @InjectMocks
    private DataServiceImpl dataService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private TrafficRepository trafficRepository;
    @Mock
    private PedestrianBicyclistRepository pedestrianBicyclistRepository;
    @Mock
    private CrashDataRepository crashDataRepository;
    @Mock
    private PedestrianBicyclistValuesRepository pedestrianBicyclistValuesRepository;
    @Mock
    private DataConverter dataConverter;
    @Mock
    private TrafficSpecification trafficSpecification;
    @Mock
    private CrashDataSpecification crashDataSpecification;
    @Mock
    private PedestrianBicyclistSpecification bicyclistSpecification;
    private Document document = new Document(123L, LocalDateTime.now(), new User(), 3 );
    private DocumentDto documentDto = new DocumentDto();

    private Long id = 1L;
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    public void should_SuccessfullyReturnAllTraffic() {
        Page<Traffic> expectedTraffic = new PageImpl<>(List.of());
        Page<TrafficDto> expectedTrafficDto = new PageImpl<>(List.of());
        when(trafficRepository.findAll(
            trafficSpecification.buildTrafficSpecification(any(TrafficFilter.class)), pageRequest))
            .thenReturn(expectedTraffic);
        Page<TrafficDto> actualTrafficDto = dataService.findAllTraffic(
            any(TrafficFilter.class), pageRequest);
        assertEquals(expectedTrafficDto, actualTrafficDto);
    }
    @Test
    public void should_SuccessfullyReturnAllPedestrianAndBicyclist() {
        Page<PedestrianBicyclist> expectedBicyclist = new PageImpl<>(List.of());
        Page<PedestrianBicyclistDto> expectedBicyclistDto = new PageImpl<>(List.of());
        when(pedestrianBicyclistRepository.findAll(
            bicyclistSpecification.buildPedestrianBicyclistSpecification(
                any(PedestrianBicyclistFilter.class)), pageRequest)).thenReturn(expectedBicyclist);
        Page<PedestrianBicyclistDto> actualBicyclistDto =  dataService.findAllPedestrianAndBicyclist(
            any(PedestrianBicyclistFilter.class), pageRequest);
        assertEquals(expectedBicyclistDto, actualBicyclistDto);
    }

    @Test
    public void should_SuccessfullyReturnAllCrashData() {
        Page<CrashData> expectedCrashData = new PageImpl<>(List.of());
        Page<CrashDataDto> expectedCrashDataDto = new PageImpl<>(List.of());
        when(crashDataRepository.findAll(
            crashDataSpecification.buildCrashDataSpecification(any(CrashDataFilter.class)),
            pageRequest)).thenReturn(expectedCrashData);
        Page<CrashDataDto> actualCrashDataDto = dataService.findAllCrashData(
            any(CrashDataFilter.class), pageRequest);
        assertEquals(expectedCrashDataDto, actualCrashDataDto);
    }

    @Test
    public void should_SuccessfullyFindUploadedDocumentById() {
        when(documentRepository.findByIdIfExists(anyLong())).thenReturn(document);
        when(dataConverter.convertToDocumentDto(any(Document.class)))
                .thenReturn(documentDto);
        assertEquals(documentDto, dataService.findUploadedDocumentById(anyLong()));
    }

    @Test
    public void should_SuccessfullyDeleteTrafficById() {
        doNothing().when(trafficRepository).deleteById(id);
        dataService.deleteTrafficById(id);
        verify(trafficRepository).findByIdIfExists(id);
        verify(trafficRepository).deleteById(id);
    }

    @Test
    public void should_SuccessfullyDeleteCrashDataById() {
        doNothing().when(crashDataRepository).deleteById(id);
        dataService.deleteCrashDataById(id);
        verify(crashDataRepository).findByIdIfExists(id);
        verify(crashDataRepository).deleteById(id);
    }

    @Test
    public void should_SuccessfullyDeletePedestrianAndBicyclistById() {
        doNothing().when(pedestrianBicyclistRepository).deleteById(id);
        dataService.deletePedestrianAndBicyclistById(id);
        verify(pedestrianBicyclistRepository).findByIdIfExists(id);
        verify(pedestrianBicyclistValuesRepository).deleteByPedestrianBicyclist(pedestrianBicyclistRepository.getEntityById(id));
        verify(pedestrianBicyclistRepository).deleteById(id);
    }

    @Test
    public void should_SuccessfullyDeleteTrafficByDocumentId() {
        doNothing().when(trafficRepository).deleteByDocumentId(id);
        dataService.deleteTrafficByDocumentId(id);
        verify(trafficRepository).existsByDocumentIdOrThrow(id);
        verify(trafficRepository).deleteByDocumentId(id);
    }

    @Test
    public void should_SuccessfullyDeleteCrashDataByDocumentId() {
        doNothing().when(crashDataRepository).deleteByDocumentId(id);
        dataService.deleteCrashDataByDocumentId(id);
        verify(crashDataRepository).existsByDocumentIdOrThrow(id);
        verify(crashDataRepository).deleteByDocumentId(id);
    }

    @Test
    public void should_SuccessfullyDeletePedestrianAndBicyclistByDocumentId() {
        doNothing().when(pedestrianBicyclistRepository).deleteByDocumentId(id);
        dataService.deletePedestrianAndBicyclistByDocumentId(id);
        verify(pedestrianBicyclistRepository).existsByDocumentIdOrThrow(id);
        verify(pedestrianBicyclistRepository).deleteByDocumentId(id);
    }
}
