package com.syberry.poc.data.service;

import com.syberry.poc.data.dto.UploadReportDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for document upload.
 */
public interface CsvProcessingService {
  UploadReportDto processCsvDocument(MultipartFile file);
}
