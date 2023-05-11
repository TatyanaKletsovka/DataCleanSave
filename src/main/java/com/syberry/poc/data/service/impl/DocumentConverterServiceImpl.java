package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.service.DocumentConverterService;
import com.syberry.poc.data.util.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

/**
 * Service for converting parsed document to processable structure for processing algorithms.
 */
@Service
@RequiredArgsConstructor
public class DocumentConverterServiceImpl implements DocumentConverterService {
  /**
   * Prepare parsed document to processing.
   * Calls converter methods based on the uploaded file name.
   *
   * @param fileName an uploaded file original name.
   * @param documentData a list of document's rows.
   * @return converted document's representation.
   */
  public List<Map<String, String>> prepareDocumentToProcessing(
      String fileName,
      List<CSVRecord> documentData) {
    List<Map<String, String>> convertedDocument;

    if (fileName.contains(Constants.pedestrianDocument)) {
      convertedDocument = convertPedestrianDocument(documentData);
    } else {
      convertedDocument = convertDefaultDocument(documentData);
    }

    return convertedDocument;
  }

  /**
   * Converts pedestrian and bicyclist document.
   *
   * @param documentData a list of document's rows.
   * @return converted document's representation.
   */
  private List<Map<String, String>> convertPedestrianDocument(List<CSVRecord> documentData) {
    List<Map<String, String>> convertedDocument = new ArrayList<>();

    for (CSVRecord row : documentData) {
      Map<String, String> rowMap = clearHeaders(row.toMap());
      Map<String, String> convertedRowData = new HashMap<>();

      String[] staticData = getStaticData(rowMap, convertedRowData);

      String staticDataHeader = staticData[0];
      String staticDataValue = staticData[1];

      convertedDocument = convertedRowData.entrySet().stream()
        .map(entry -> {
          String newColumnName = entry.getKey();
          String newColumnValue = entry.getValue();

          Map<String, String> rowValues = new HashMap<>();
          rowValues.put(staticDataHeader, staticDataValue);
          rowValues.put("column_name", newColumnName);
          rowValues.put("column_value", newColumnValue);

          return rowValues;
        })
        .collect(Collectors.toList());
    }

    return convertedDocument;
  }

  private String[] getStaticData(Map<String, String> rowMap, Map<String, String> convertedRowData) {
    return rowMap.entrySet().stream()
        .filter(entry -> entry.getValue() != null && !entry.getValue().isBlank())
        .map(entry -> {
          String columnName = entry.getKey().toLowerCase();
          String columnValue = entry.getValue();

          if (columnName.contains("time")) {
            return new String[]{"full_date", columnValue};
          } else if (columnName.contains("date")) {
            return new String[]{"date", columnValue};
          } else {
            convertedRowData.put(columnName, columnValue);
            return null;
          }
        })
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(new String[]{"", ""});
  }

  /**
   * Convert default document.
   *
   * @param documentData a list of document's rows.
   * @return converted document's representation.
   */
  private List<Map<String, String>> convertDefaultDocument(List<CSVRecord> documentData) {
    return documentData.stream()
      .map(row -> clearHeaders(row.toMap()))
      .collect(Collectors.toList());
  }

  /**
   * Clears document's row's headers before processing.
   *
   * @param convertedDocument a list of document's rows.
   * @return converted document's representation with cleared headers.
   */
  private Map<String, String> clearHeaders(Map<String, String> convertedDocument) {
    return convertedDocument.entrySet().stream()
      .collect(Collectors.toMap(
        entry -> entry.getKey().trim().toLowerCase().replaceAll("\\s+", "_")
          .replaceAll("[^\\w_]", ""),
        Map.Entry::getValue
      ));
  }
}
