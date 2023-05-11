package com.syberry.poc.data.service;

import com.syberry.poc.data.dto.enums.Tag;
import java.util.List;
import java.util.Map;

/**
 * Service interface for document's data mapping to tags.
 */
public interface DataMapperService {
  Map<String, List<Tag>> mapColumns(List<Map<String, String>> documentData);
}

