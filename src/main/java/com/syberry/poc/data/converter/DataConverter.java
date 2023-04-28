package com.syberry.poc.data.converter;

import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.PedestrianBicyclistValuesDto;
import com.syberry.poc.data.dto.TrafficDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A component that provides converting methods for the Data entities.
 */
@Component
@RequiredArgsConstructor
public class DataConverter {

  /**
   * Converts a CrashData object to a CrashDataDto object.
   *
   * @param crashData the CrashData entity to be converted.
   * @return dto the CrashDataDto object to the given entity.
   */
  public CrashDataDto convertToCrashDataDto(CrashData crashData) {
    return CrashDataDto.builder()
        .id(crashData.getId())
        .year(crashData.getYear())
        .month(crashData.getMonth())
        .day(crashData.getDay())
        .hour(crashData.getHour())
        .weekend(String.valueOf(crashData.getWeekend()))
        .collisionType(crashData.getCollisionType())
        .injuryType(String.valueOf(crashData.getInjuryType()))
        .primaryFactor(crashData.getPrimaryFactor())
        .reportedLocation(crashData.getReportedLocation())
        .latitude(crashData.getLatitude())
        .longitude(crashData.getLongitude())
        .documentId(crashData.getDocument().getId())
        .build();
  }

  /**
   * Converts a PedestrianBicyclist object to a PedestrianBicyclistDto object.
   *
   * @param pedestrianBicyclist the PedestrianBicyclist entity to be converted.
   * @return dto the PedestrianBicyclistDto object to the given entity.
   */
  public PedestrianBicyclistDto convertToPedestrianBicyclistDto(
      PedestrianBicyclist pedestrianBicyclist) {
    return PedestrianBicyclistDto.builder()
        .id(pedestrianBicyclist.getId())
        .year(pedestrianBicyclist.getYear())
        .month(pedestrianBicyclist.getMonth())
        .day(pedestrianBicyclist.getDay())
        .weekDay(String.valueOf(pedestrianBicyclist.getWeekDay()))
        .documentId(pedestrianBicyclist.getDocument().getId())
        .listValues(getValuesDto(pedestrianBicyclist.getValues()))
        .build();
  }

  /**
   * Converts a PedestrianBicyclistValues object list to a
   * PedestrianBicyclistValuesDto object list.
   *
   * @param dtoList the PedestrianBicyclistValues entity list to be converted.
   * @return dto the PedestrianBicyclistValuesDto objects list to the given list of entities.
   */
  private List<PedestrianBicyclistValuesDto> getValuesDto(
      List<PedestrianBicyclistValues> dtoList) {
    List<PedestrianBicyclistValuesDto> valuesDtolist = new ArrayList<>();
    for (PedestrianBicyclistValues element : dtoList) {
      valuesDtolist.add(PedestrianBicyclistValuesDto.builder()
          .id(element.getId())
          .value(element.getValue())
          .columnName(element.getColumnName())
          .build());
    }
    return valuesDtolist;
  }

  /**
   * Converts a Traffic object to a TrafficDto object.
   *
   * @param traffic the Traffic entity to be converted.
   * @return dto the DocumentDto object to the given entity.
   */
  public TrafficDto convertToTrafficDto(Traffic traffic) {
    return TrafficDto.builder()
        .id(traffic.getId())
        .county(traffic.getCounty())
        .community(traffic.getCommunity())
        .onRoad(traffic.getOnRoad())
        .fromRoad(traffic.getFromRoad())
        .toRoad(traffic.getToRoad())
        .approach(traffic.getApproach())
        .at(traffic.getAt())
        .direction(String.valueOf(traffic.getDirection()))
        .directions(traffic.getDirections())
        .latitude(traffic.getLatitude())
        .longitude(traffic.getLongitude())
        .documentId(traffic.getDocument().getId())
        .build();
  }

  /**
   * Converts a Document object to a DocumentDto object.
   *
   * @param document the Document entity to be converted.
   * @return dto the DocumentDto object to the given entity.
   */
  public DocumentDto convertToDocumentDto(Document document) {
    return DocumentDto.builder()
        .id(document.getId())
        .dateTime(document.getDateTime())
        .userId(document.getUser().getId())
        .processedRows(document.getProcessedRows())
        .build();
  }
}
