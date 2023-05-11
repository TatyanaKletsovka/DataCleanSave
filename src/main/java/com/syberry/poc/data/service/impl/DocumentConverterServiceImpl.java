package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.service.DocumentConverterService;
import com.syberry.poc.data.util.ColumnNameConstants;
import com.syberry.poc.data.util.FileNameConstants;
import com.syberry.poc.data.util.PatternConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    if (fileName.contains(FileNameConstants.pedestrianDocument)) {
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

      String staticDataHeader = "";
      String staticDataValue = "";

      for (Map.Entry<String, String> rowValue : rowMap.entrySet()) {
        String columnName = rowValue.getKey();
        String columnValue = rowValue.getValue();

        if (columnName.toLowerCase().contains("time")) {
          staticDataHeader = "full_date";
          staticDataValue = columnValue;
        } else if (columnName.toLowerCase().contains(ColumnNameConstants.DATE)) {
          staticDataHeader = ColumnNameConstants.DATE;
          staticDataValue = columnValue;
        } else if (!columnValue.isBlank()) {
          convertedRowData.put(columnName, columnValue);
        }
      }

      for (Map.Entry<String, String> rowValue : convertedRowData.entrySet()) {
        String newColumnName = rowValue.getKey();
        String newColumnValue = rowValue.getValue();

        Map<String, String> rowValues = new HashMap<>();
        rowValues.put(staticDataHeader, staticDataValue);
        rowValues.put(ColumnNameConstants.COLUMN_NAME, newColumnName);
        rowValues.put(ColumnNameConstants.COLUMN_VALUE, newColumnValue);

        convertedDocument.add(rowValues);
      }
    }

    return convertedDocument;
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
        entry -> entry.getKey().trim().toLowerCase()
            .replaceAll(PatternConstants.SPACES_PATTERN, PatternConstants.REPLACE_WITH_PATTERN)
            .replaceAll(PatternConstants.HEADER_REPLACEMENT_PATTERN,
                PatternConstants.REPLACE_CLEAN_PATTERN),
        Map.Entry::getValue
      ));
  }
}
