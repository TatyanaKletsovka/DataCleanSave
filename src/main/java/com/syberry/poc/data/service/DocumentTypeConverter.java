package com.syberry.poc.data.service;

import com.syberry.poc.data.database.entity.Document;
import java.util.List;
import java.util.Map;

/**
 * Service interface for document's types converting.
 */
public interface DocumentTypeConverter {
  int save(List<Map<String, String>> proceededDocument, Document document);
}
