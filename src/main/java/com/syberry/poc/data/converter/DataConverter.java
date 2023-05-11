package com.syberry.poc.data.converter;

import static com.syberry.poc.data.util.HashCalculator.calculateHash;

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
import com.syberry.poc.data.util.ColumnNameConstants;
import com.syberry.poc.data.util.PatternConstants;
import com.syberry.poc.user.database.entity.User;
import java.lang.String;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


/**
 * A component that provides converting methods for the Data entities.
 */
@Component
@Slf4j
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
      if (documentRow.get(ColumnNameConstants.INJURY_TYPE) != null) {
        injuryType = InjuryType.valueOf(documentRow.get(ColumnNameConstants.INJURY_TYPE)
            .toUpperCase()
            .replaceAll(PatternConstants.REPLACEMENT_PATTERN,
                PatternConstants.REPLACE_WITH_PATTERN));
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
    String collisionType = documentRow.get(ColumnNameConstants.COLLISION_TYPE);
    String primaryFactor = documentRow.get(ColumnNameConstants.PRIMARY_FACTOR);
    String reportedLocation = documentRow.get(ColumnNameConstants.REPORTED_LOCATION);
    return CrashData.builder()
        .year(Integer.parseInt(documentRow.get(ColumnNameConstants.YEAR)))
        .month(Integer.parseInt(documentRow.get(ColumnNameConstants.MONTH)))
        .day(Integer.parseInt(documentRow.get(ColumnNameConstants.DAY)))
        .hour(Integer.parseInt(documentRow.get(ColumnNameConstants.HOUR)))
        .weekend(Weekend.valueOf(documentRow.get(ColumnNameConstants.WEEKEND).toUpperCase()))
        .collisionType(collisionType)
        .collisionTypeHash(calculateHash(collisionType))
        .primaryFactor(primaryFactor)
        .primaryFactorHash(calculateHash(primaryFactor))
        .injuryType(injuryType)
        .reportedLocation(reportedLocation)
        .reportedLocationHash(calculateHash(reportedLocation))
        .latitude(parseFloat(documentRow.get(ColumnNameConstants.LATITUDE)))
        .longitude(parseFloat(documentRow.get(ColumnNameConstants.LONGITUDE)))
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
    String county = documentRow.get(ColumnNameConstants.COUNTY);
    String community = documentRow.get(ColumnNameConstants.COMMUNITY);
    String onRoad = documentRow.get(ColumnNameConstants.ON);
    String fromRoad = documentRow.get(ColumnNameConstants.FROM);
    String toRoad = documentRow.get(ColumnNameConstants.TO);
    String approach = documentRow.get(ColumnNameConstants.APPROACH);
    String at = documentRow.get(ColumnNameConstants.AT);
    String directions = documentRow.get(ColumnNameConstants.DIRECTIONS);
    return Traffic.builder()
        .county(county)
        .countyHash(calculateHash(county))
        .community(community)
        .communityHash(calculateHash(community))
        .onRoad(onRoad)
        .onRoadHash(calculateHash(onRoad))
        .fromRoad(fromRoad)
        .fromRoadHash(calculateHash(fromRoad))
        .toRoad(toRoad)
        .toRoadHash(calculateHash(toRoad))
        .approach(approach)
        .approachHash(calculateHash(approach))
        .at(at)
        .atHash(calculateHash(at))
        .direction(Direction.valueOf(documentRow.get(ColumnNameConstants.DIR)
            .replace("1", "one")
            .replace("2", "two")
            .toUpperCase()
            .replaceAll(PatternConstants.REPLACEMENT_PATTERN,
                PatternConstants.REPLACE_WITH_PATTERN)))
        .directions(directions)
        .directionsHash(calculateHash(directions))
        .latitude(parseFloat(documentRow.get(ColumnNameConstants.LATITUDE)))
        .longitude(parseFloat(documentRow.get(ColumnNameConstants.LONGITUDE)))
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
      List<Map<String, String>> documentData,
      Document document) {
    List<PedestrianBicyclist> entities = new ArrayList<>();
    Set<String> uniqueDates = new HashSet<>();

    for (Map<String, String> documentRow : documentData) {
      String dateValue = documentRow.get(ColumnNameConstants.DATE);

      if (uniqueDates.contains(dateValue)) {
        continue;
      }
      Map<String, Integer> dateValues;
      try {
        dateValues = convertToDateValues(dateValue);
      } catch (ParseException e) {
        log.info(String.format("Invalid data: %s", dateValue));
        continue;
      }
      entities.add(convertToPedestrianBicyclist(dateValues, document));
      uniqueDates.add(dateValue);
    }

    return entities;
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
        .year(documentRow.get(ColumnNameConstants.YEAR))
        .month(documentRow.get(ColumnNameConstants.MONTH))
        .day(documentRow.get(ColumnNameConstants.DAY))
        .weekDay(weekDays[documentRow.get(ColumnNameConstants.WEEKDAY) - 1])
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
      String dateValue = documentRow.get(ColumnNameConstants.DATE);

      Map<String, Integer> dateValues = null;
      try {
        dateValues = convertToDateValues(dateValue);
      } catch (ParseException e) {
        log.info(String.format("Invalid data: %s", dateValue));
        continue;
      }

      int dayOfWeek = dateValues.get(ColumnNameConstants.WEEKDAY);
      WeekDay[] weekDays = WeekDay.values();
      WeekDay weekDay = weekDays[dayOfWeek - 1];

      Optional<PedestrianBicyclist> pedestrianBicyclist = pedestrianBicyclistRepository
          .findTopByYearAndMonthAndDayAndWeekDayOrderByIdDesc(
              dateValues.get(ColumnNameConstants.YEAR),
              dateValues.get(ColumnNameConstants.MONTH),
              dateValues.get(ColumnNameConstants.DAY),
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
    String columnName = documentRow.get(ColumnNameConstants.COLUMN_NAME);
    String value = documentRow.get(ColumnNameConstants.COLUMN_VALUE);
    return PedestrianBicyclistValues.builder()
        .columnName(columnName)
        .columnNameHash(calculateHash(columnName))
        .value(value)
        .valueHash(calculateHash(value))
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
  private static Map<String, Integer> convertToDateValues(String dateValue) throws ParseException {
    Map<String, Integer> dateValues;
    SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.US);
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = format.parse(dateValue);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    dateValues = new HashMap<>();
    dateValues.put(ColumnNameConstants.YEAR, calendar.get(Calendar.YEAR));
    dateValues.put(ColumnNameConstants.MONTH, calendar.get(Calendar.MONTH) + 1);
    dateValues.put(ColumnNameConstants.DAY, calendar.get(Calendar.DAY_OF_MONTH));
    dateValues.put(ColumnNameConstants.WEEKDAY, calendar.get(Calendar.DAY_OF_WEEK));

    return dateValues;
  }

  /**
   * Convert coordinate from string Float.
   *
   * @param coordinate the coordinate value
   * @return Float coordinate or null in string is invalid
   */
  private Float parseFloat(String coordinate) {
    return !StringUtils.isBlank(coordinate) ? Float.parseFloat(coordinate) : null;
  }
}
