package com.syberry.poc.data.converter;

import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.database.repository.PedestrianBicyclistRepository;
import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.PedestrianBicyclistValuesDto;
import com.syberry.poc.data.dto.TrafficDto;
import com.syberry.poc.data.dto.enums.Direction;
import com.syberry.poc.data.dto.enums.InjuryType;
import com.syberry.poc.data.dto.enums.WeekDay;
import com.syberry.poc.data.dto.enums.Weekend;
import com.syberry.poc.exception.DateParseException;
import com.syberry.poc.user.database.entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A component that provides converting methods for the Data entities.
 */
@Component
@RequiredArgsConstructor
public class DataConverter {
  private final PedestrianBicyclistRepository pedestrianBicyclistRepository;

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

  /**
   *  Converts document data to a list of CrashData entities.
   *
   * @param documentData the list of document rows.
   * @param document the Document entity.
   * @return the list of CrashData entities.
   */
  public List<CrashData> convertToCrashDataList(
      List<Map<String, String>> documentData,
      Document document) {
    return documentData.stream().map(documentRow -> {
      InjuryType injuryType = null;
      if (documentRow.get("injury_type") != null) {
        injuryType = InjuryType.valueOf(documentRow.get("injury_type").toUpperCase()
          .replaceAll("[-/\\s]+", "_"));
      }
      return convertToCrashData(documentRow, injuryType, document);
    }).collect(Collectors.toList());
  }

  /**
   *  Converts document row to CrashData entity.
   *
   * @param documentRow the map with column names and values
   * @param injuryType the InjuryType entity
   * @param document the Document entity
   * @return the CrashData entity
   */
  private CrashData convertToCrashData(
      Map<String, String> documentRow, InjuryType injuryType, Document document) {
    return CrashData.builder()
        .year(Integer.parseInt(documentRow.get("year")))
        .month(Integer.parseInt(documentRow.get("month")))
        .day(Integer.parseInt(documentRow.get("day")))
        .hour(Integer.parseInt(documentRow.get("hour")))
        .weekend(Weekend.valueOf(documentRow.get("weekend").toUpperCase()))
        .collisionType(documentRow.get("collision_type"))
        .primaryFactor(documentRow.get("primary_factor"))
        .injuryType(injuryType)
        .reportedLocation(documentRow.get("reported_location"))
        .latitude(Float.parseFloat(documentRow.get("latitude")))
        .longitude(Float.parseFloat(documentRow.get("longitude")))
        .document(document)
        .build();
  }

  /**
   *  Converts document data to a list of Traffic entities.
   *
   * @param documentData the list of document rows.
   * @param document the Document entity.
   * @return the list of Traffic entities.
   */
  public List<Traffic> convertToTrafficList(
      List<Map<String, String>> documentData, Document document) {
    return documentData.stream()
      .map(documentRow -> convertToTraffic(documentRow, document))
      .collect(Collectors.toList());
  }

  /**
   *  Converts document row to Traffic entity.
   *
   * @param documentRow the map with column names and values
   * @param document the Document entity
   * @return the Traffic entity
   */
  private Traffic convertToTraffic(Map<String, String> documentRow, Document document) {
    return Traffic.builder()
        .county(documentRow.get("county"))
        .community(documentRow.get("community"))
        .onRoad(documentRow.get("on"))
        .fromRoad(documentRow.get("from"))
        .toRoad(documentRow.get("to"))
        .approach(documentRow.get("approach"))
        .at(documentRow.get("at"))
        .direction(Direction.valueOf(documentRow.get("dir")
            .replace("1", "one")
            .replace("2", "two")
            .toUpperCase()
            .replaceAll("[-/\\s]+", "_")))
        .directions(documentRow.get("directions"))
        .latitude(Float.parseFloat(documentRow.get("latitude")))
        .longitude(Float.parseFloat(documentRow.get("longitude")))
        .document(document)
        .build();
  }

  /**
   *  Converts document data to a list of PedestrianBicyclist entities.
   *
   * @param documentData the list of document rows.
   * @param document the Document entity.
   * @return the list of PedestrianBicyclist entities.
   */
  public List<PedestrianBicyclist> convertToPedestrianBicyclistList(
      List<Map<String, String>> documentData, Document document) {
    Set<String> uniqueDates = new HashSet<>();
    return documentData.stream()
      .map(documentRow -> documentRow.get("date"))
      .filter(dateValue -> !uniqueDates.contains(dateValue))
      .map(dateValue -> {
        uniqueDates.add(dateValue);
        return getDateValues(dateValue);
      })
      .map(dateValues -> convertToPedestrianBicyclist(dateValues, document))
      .collect(Collectors.toList());
  }

  /**
   *  Converts document row to PedestrianBicyclist entity.
   *
   * @param documentRow the map with column names and values
   * @param document the Document entity
   * @return the PedestrianBicyclist entity
   */
  private PedestrianBicyclist convertToPedestrianBicyclist(
      Map<String, Integer> documentRow, Document document) {
    WeekDay[] weekDays = WeekDay.values();
    return PedestrianBicyclist.builder()
        .year(documentRow.get("year"))
        .month(documentRow.get("month"))
        .day(documentRow.get("day"))
        .weekDay(weekDays[documentRow.get("weekDay") - 1])
        .document(document)
        .build();
  }

  /**
   *  Converts document data to a list of PedestrianBicyclistValues entities.
   *
   * @param documentData the list of document rows.
   * @return the list of PedestrianBicyclistValues entities.
   */
  public List<PedestrianBicyclistValues> convertToPedestrianBicyclistValuesList(
      List<Map<String, String>> documentData) {
    List<PedestrianBicyclistValues> entities = new ArrayList<>();

    for (Map<String, String> documentRow : documentData) {
      String dateValue = documentRow.get("date");

      Map<String, Integer> dateValues = getDateValues(dateValue);

      int dayOfWeek = dateValues.get("weekDay");
      WeekDay[] weekDays = WeekDay.values();
      WeekDay weekDay = weekDays[dayOfWeek - 1];

      Optional<PedestrianBicyclist> pedestrianBicyclist = pedestrianBicyclistRepository
          .findTopByYearAndMonthAndDayAndWeekDayOrderByIdDesc(
              dateValues.get("year"),
              dateValues.get("month"),
              dateValues.get("day"),
              weekDay
          );

      pedestrianBicyclist.ifPresent(bicyclist
          -> entities.add(convertToPedestrianBicyclistValue(documentRow, bicyclist)));
    }
    return entities;
  }

  /**
   *  Converts document row to PedestrianBicyclistValue entity.
   *
   * @param documentRow the map with column names and values
   * @return the PedestrianBicyclistValue entity
   */
  private PedestrianBicyclistValues convertToPedestrianBicyclistValue(
      Map<String, String> documentRow, PedestrianBicyclist pedestrianBicyclist) {
    return PedestrianBicyclistValues.builder()
        .columnName(documentRow.get("column_name"))
        .value(documentRow.get("column_value"))
        .pedestrianBicyclist(pedestrianBicyclist)
        .build();
  }

  /**
   *  Prepare document entity PedestrianBicyclistValues entities.
   *
   * @param user the current system's user.
   * @param processedRows quantity of processed document's rows.
   * @return the Document entity.
   */
  public Document convertToDocument(User user, int processedRows) {
    return Document.builder()
      .processedRows(processedRows)
      .user(user)
      .build();
  }

  /**
   * Convert date from string to Map representation.
   *
   * @param dateValue the date value.
   * @return the map of date values: year, month, day, weekday.
   */
  private static Map<String, Integer> getDateValues(String dateValue) {
    Map<String, Integer> dateValues;
    try {
      SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date date = format.parse(dateValue);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);

      dateValues = new HashMap<>();
      dateValues.put("year", calendar.get(Calendar.YEAR));
      dateValues.put("month", calendar.get(Calendar.MONTH) + 1);
      dateValues.put("day", calendar.get(Calendar.DAY_OF_MONTH));
      dateValues.put("weekDay", calendar.get(Calendar.DAY_OF_WEEK));
    } catch (ParseException ignored) {
      throw new DateParseException("An error occurred on parsing date string " + dateValue);
    }

    return dateValues;
  }
}
