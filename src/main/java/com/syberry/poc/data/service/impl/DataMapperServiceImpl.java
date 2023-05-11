package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.dto.enums.Column;
import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.DataMapperService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for matching document's table headers with processing tags.
 */
@Service
@Slf4j
public class DataMapperServiceImpl implements DataMapperService {
  /**
   * Returns accordance of columns to processing tags.
   *
   * @param documentData a list of document's rows.
   * @return the tags map to document's headers.
   */
  @Override
  public Map<String, List<Tag>> mapColumns(List<Map<String, String>> documentData) {
    return documentData.get(0).keySet().stream()
      .collect(Collectors.toMap(
        Function.identity(),
        columnName -> {
          try {
            Column currentColumn = Column.valueOf(columnName.toUpperCase().replace(' ', '_'));
            return currentColumn.tags;
          } catch (IllegalArgumentException ignored) {
            log.error("No tags applied to column " + columnName);
            return Collections.emptyList();
          }
        }
      ));
  }
}
