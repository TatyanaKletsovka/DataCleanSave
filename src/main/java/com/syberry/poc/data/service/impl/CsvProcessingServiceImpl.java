package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.repository.DocumentRepository;
import com.syberry.poc.data.dto.UploadReportDto;
import com.syberry.poc.data.dto.enums.DocumentType;
import com.syberry.poc.data.dto.enums.Tag;
import com.syberry.poc.data.service.CsvProcessingService;
import com.syberry.poc.data.service.DataMapperService;
import com.syberry.poc.data.service.DataProcessingAlgorithmsService;
import com.syberry.poc.data.service.DocumentConverterService;
import com.syberry.poc.data.service.DocumentTypeConverter;
import com.syberry.poc.data.service.SavingDataService;
import com.syberry.poc.exception.FileReadingException;
import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *  Service for uploaded documents processing.
 */
@Service
@RequiredArgsConstructor
public class CsvProcessingServiceImpl implements CsvProcessingService {
  private final DataConverter dataConverter;
  private final SavingDataService savingDataService;
  private final DataProcessingAlgorithmsService dataProcessingAlgorithmsService;
  private final DataMapperService dataMapperService;
  private final DocumentConverterService documentConverterService;
  private final UserRepository userRepository;
  private final DocumentRepository documentRepository;

  @Autowired
  private Map<DocumentType, DocumentTypeConverter> documentMapConverter;

  /**
   *  Process uploaded file then returns uploading report.
   *
   * @param file a file that uploaded via application endpoint.
   * @return report with uploaded and processed lines counters.
   */
  public UploadReportDto processCsvDocument(MultipartFile file) {
    String fileName = file.getOriginalFilename().toLowerCase();

    List<CSVRecord> parsedDocument = parseCsvDocument(file);
    List<Map<String, String>> convertedDocument = documentConverterService
        .prepareDocumentToProcessing(fileName, parsedDocument);
    Map<String, List<Tag>> columnsTags = dataMapperService.mapColumns(convertedDocument);
    List<Map<String, String>> proceededDocument = dataProcessingAlgorithmsService
        .applyProcessingAlgorithms(convertedDocument, columnsTags);

    User user = getCurrentUser();
    Document document = dataConverter.convertToDocument(user, parsedDocument.size());
    documentRepository.save(document);
    int uploadedDocumentSize = saveDocumentDataByType(fileName, proceededDocument, document);

    return new UploadReportDto(parsedDocument.size(), uploadedDocumentSize);
  }

  /**
   *  Parse document file to in order to prepare it to processing.
   *
   * @param file a document file.
   * @return list of CSVRecord instances with document's rows inside.
   */
  private List<CSVRecord> parseCsvDocument(MultipartFile file) {
    try (Reader reader = new InputStreamReader(file.getInputStream())) {
      CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().build();
      CSVParser csvParser = new CSVParser(reader, csvFormat);

      return new ArrayList<>(csvParser.getRecords());
    } catch (IOException ioException) {
      throw new FileReadingException("An error occurred while reading file: ", ioException);
    } catch (IllegalArgumentException e) {
      throw new ValidationException(String.format("Incorrect header: %s", e));
    }
  }

  /**
   * Gets current authorized user's entity.
   *
   * @return a User entity.
   */
  private User getCurrentUser() {
    return userRepository.findByIdIfExists((long) 1);
  }

  private int saveDocumentDataByType(
      String fileName, List<Map<String, String>> proceededDocument,
      Document document) {
    String convertExceptionMessage = "Document type is not supported";
    DocumentType documentType = null;
    for (DocumentType type : DocumentType.values()) {
      if (fileName.contains(type.getType())) {
        documentType = type;
        break;
      }
    }

    if (documentType == null) {
      throw new ValidationException(convertExceptionMessage);
    }

    return documentMapConverter.get(documentType).save(proceededDocument, document);
  }
}
