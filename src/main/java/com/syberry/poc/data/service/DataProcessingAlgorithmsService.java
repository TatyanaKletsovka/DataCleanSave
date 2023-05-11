package com.syberry.poc.data.service;

import com.syberry.poc.data.dto.enums.Tag;
import java.util.List;
import java.util.Map;

/**
 * Service interface for document's data processing with algorithms.
 */
public interface DataProcessingAlgorithmsService {
  List<Map<String, String>> applyProcessingAlgorithms(
          List<Map<String, String>> documentData,
          Map<String, List<Tag>> tags);
}
