package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.DataProcessingAlgorithmsService;
import com.syberry.poc.data.util.PatternConstants;
import com.syberry.poc.exception.DateProcessingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Service that applies processing algorithms to the document's data.
 */
@Service
@Setter
public class DataProcessingAlgorithmsServiceImpl implements DataProcessingAlgorithmsService {
  private static final int MINIMAL_ROWS_LIMIT = 100;
  private static final String STRING_NULL_VALUE = "No_Data";
  private static final String INTEGER_NULL_VALUE = "0";


  /**
   * Common method that calls processing methods needed based on the columns tags.
   *
   * @param documentData a list of document's rows.
   * @param tags a map of processing tags to document's headers.
   * @return processed document in the list of rows representation.
   */
  @Override
  public List<Map<String, String>> applyProcessingAlgorithms(
      List<Map<String, String>> documentData, Map<String, List<Tag>> tags) {
    int filledRowsNum = countFilledRows(documentData);
    List<Map<String, String>> proceededData;
    if (filledRowsNum >= MINIMAL_ROWS_LIMIT) {
      proceededData = proceedTableColumns(reformatNullValues(
          clearTableRows(documentData, tags), tags), tags);
    } else {
      throw new DateProcessingException(
          "Number of loaded rows is less than the minimum allowed. Number of provided rows: "
              + filledRowsNum + " ,minimum allowable number of rows: " + MINIMAL_ROWS_LIMIT);
    }
    return proceededData;
  }

  /**
   * Checks if all columns with the Obligatory tag are filled.
   *
   * @param row  to check.
   * @param tags a map of processing tags to document's headers.
   * @return true if all Obligatory columns are filled and false otherwise.
   */
  private boolean isObligatoryColumnsFulfilled(
      Map<String, String> row, Map<String, List<Tag>> tags) {
    for (Map.Entry<String, List<Tag>> entry : tags.entrySet()) {
      String columnName = entry.getKey();
      List<Tag> columnTags = entry.getValue();
      boolean isObligatory = columnTags
          .stream()
          .anyMatch(tag -> tag == Tag.OBLIGATORY);
      if (isObligatory && (StringUtils.isBlank(row.get(columnName))
          || row.get(columnName).equals(INTEGER_NULL_VALUE)
          || row.get(columnName).equals(STRING_NULL_VALUE))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Clears the table based on the column tags.
   *
   * @param documentData a list of document's rows.
   * @param tags         a map of processing tags to document's headers.
   * @return cleared document in the list of rows representation.
   */
  private List<Map<String, String>> clearTableRows(
      List<Map<String, String>> documentData, Map<String, List<Tag>> tags) {
    List<String> columnsToRemove = new ArrayList<>();
    for (String columnName : documentData.get(0).keySet()) {
      if (!tags.containsKey(columnName)) {
        columnsToRemove.add(columnName);
      }
    }
    for (String columnName : columnsToRemove) {
      deleteColumn(documentData, columnName);
    }
    return documentData;
  }

  /**
   * Deletes column from document based on the passed column name.
   *
   * @param documentData a list of document's rows.
   * @param columnName   a column name to delete from the document.
   */
  private void deleteColumn(List<Map<String, String>> documentData, String columnName) {
    for (Map<String, String> row : documentData) {
      row.remove(columnName);
    }
  }

  /**
   * Applies data processing algorithms based on the columns tags.
   *
   * @param documentData a list of document's rows.
   * @param tags a map of processing tags to document's headers.
   * @return processed document in the list of rows representation.
   */
  private List<Map<String, String>> proceedTableColumns(
      List<Map<String, String>> documentData, Map<String, List<Tag>> tags) {
    Iterator<Map<String, String>> iterator = documentData.iterator();
    while (iterator.hasNext()) {
      Map<String, String> row = iterator.next();
      if (!isObligatoryColumnsFulfilled(row, tags)) {
        iterator.remove();
      } else {
        for (Map.Entry<String, String> entry : row.entrySet()) {
          String columnName = entry.getKey();
          String columnValue = entry.getValue();
          if (tags.containsKey(columnName) && tags.get(columnName).contains(Tag.STRING)) {
            row.put(columnName, prettifyStringData(columnValue));
          } else if (tags.containsKey(columnName) && tags.get(columnName).contains(Tag.TIME)) {
            if (containsNumeric(columnValue)) {
              if (Integer.parseInt(columnValue) > 2400 || Integer.parseInt(columnValue) < 0) {
                iterator.remove();
              }
            } else {
              iterator.remove();
            }
          } else if (tags.containsKey(columnName) && tags.get(columnName).contains(Tag.INT)) {
            if (!containsNumeric(columnValue)) {
              iterator.remove();
            }
          }
        }
      }
    }
    return documentData;
  }

  /**
   * Counts the number of filled rows in the given document.
   *
   * @param documentData a list of document's rows.
   * @return number of filled rows.
   */
  private int countFilledRows(List<Map<String, String>> documentData) {
    int numFilledRows = 0;
    for (Map<String, String> ignored : documentData) {
      numFilledRows++;
    }
    return numFilledRows;
  }

  /**
   * Sets correct null values based on the column tags.
   *
   * @param documentData a list of document's rows.
   * @param tags a map of processing tags to document's headers.
   * @return processed document in the list of rows representation.
   */
  private List<Map<String, String>> reformatNullValues(
      List<Map<String, String>> documentData, Map<String, List<Tag>> tags) {
    for (Map<String, String> row : documentData) {
      for (Map.Entry<String, String> cell : row.entrySet()) {
        String columnName = cell.getKey();
        String cellValue = cell.getValue();
        List<Tag> columnTags = tags.get(columnName);
        if (cellValue.equalsIgnoreCase("null") || cellValue.isEmpty()
            || cellValue.equalsIgnoreCase("n/a")) {
          if (columnTags.contains(Tag.STRING)) {
            cell.setValue(STRING_NULL_VALUE);
          } else if (columnTags.contains(Tag.INT)) {
            cell.setValue(INTEGER_NULL_VALUE);
          }
        }
      }
    }
    return documentData;
  }

  /**
   * Checks if the value contains only numbers.
   *
   * @param value to check.
   * @return true if the value matches the pattern and false otherwise.
   */
  private boolean containsNumeric(String value) {
    return value.matches(PatternConstants.NUMERIC_PATTERN);
  }

  /**
   * Converts a string value into a prettified version by capitalizing
   *    each word and separating them with underscores.
   *
   * @param value to convert.
   * @return converted string value.
   */
  private String prettifyStringData(String value) {
    if (!value.isEmpty()) {
      return Arrays.stream(value.trim().split(PatternConstants.SPACES_PATTERN))
          .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
          .collect(Collectors.joining("_"));
    } else {
      return value;
    }
  }
}
