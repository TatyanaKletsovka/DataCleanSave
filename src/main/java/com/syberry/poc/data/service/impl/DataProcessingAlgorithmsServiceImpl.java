package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.DataProcessingAlgorithmsService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Service that applies processing algorithms to the document's data.
 */
@Service
public class DataProcessingAlgorithmsServiceImpl implements DataProcessingAlgorithmsService {
  /**
   * Common method that calls processing methods needed based on the columns tags.
   *
   * @param documentData a list of document's rows.
   * @param tags a map of processing tags to document's headers.
   * @return processed document in the list of rows representation.
   */
  @Override
  public List<Map<String, String>> applyProcessingAlgorithms(List<Map<String, String>> documentData,
                                                             Map<String, List<Tag>> tags) {
    return documentData;
  }
}
