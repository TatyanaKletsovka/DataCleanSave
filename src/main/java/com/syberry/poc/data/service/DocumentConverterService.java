package com.syberry.poc.data.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVRecord;

/**
 * Service interface for document's data converting
 * in order to get processable document representation.
 */
public interface DocumentConverterService {
  List<Map<String, String>> prepareDocumentToProcessing(
      String fileName,
      List<CSVRecord> documentData);
}
