package com.syberry.poc.data.controller;

import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.CrashDataFilter;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.PedestrianBicyclistFilter;
import com.syberry.poc.data.dto.TrafficDto;
import com.syberry.poc.data.dto.TrafficFilter;
import com.syberry.poc.data.dto.UploadReportDto;
import com.syberry.poc.data.service.CsvProcessingService;
import com.syberry.poc.data.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * A controller for handling data-related HTTP requests.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {
  private final DataService dataService;
  private final CsvProcessingService csvProcessingService;

  /**
   * Returns a paginated list of all traffic data records in the system.
   *
   * @param filter The filter used to find Traffic.
   * @param pageable is pagination parameter
   * @return page object of all traffic data records
   */
  @GetMapping("/traffic")
  public Page<TrafficDto> findAllTraffic(TrafficFilter filter, Pageable pageable) {
    log.info("GET-request: getting all traffic objects");
    return dataService.findAllTraffic(filter, pageable);
  }

  /**
   * Returns a paginated list of all pedestrian and bicyclist data records in the system.
   *
   * @param filter The filter used to find PedestrianBicyclist.
   * @param pageable is pagination parameter
   * @return page object of all pedestrian and bicyclist data records
   */
  @GetMapping("/pedestrian-and-bicyclist")
  public Page<PedestrianBicyclistDto> findAllPedestrianAndBicyclist(
      PedestrianBicyclistFilter filter, Pageable pageable) {
    log.info("GET-request: getting all pedestrian and bicyclist objects");
    return dataService.findAllPedestrianAndBicyclist(filter, pageable);
  }

  /**
   * Returns a paginated list of all crash data records in the system.
   *
   * @param filter The filter used to find CrashData.
   * @param pageable is pagination parameter
   * @return page object of all crash data records
   */
  @GetMapping("/crash-data")
  public Page<CrashDataDto> findAllCrashData(CrashDataFilter filter, Pageable pageable) {
    log.info("GET-request: getting all crash data objects");
    return dataService.findAllCrashData(filter, pageable);
  }

  /**
   * Returns the document with the specified ID.
   *
   * @param id the ID of the uploaded document to return
   * @return the document with the specified ID
   */
  @GetMapping("/document/{id}")
  public DocumentDto findUploadedDocumentById(@PathVariable("id") Long id) {
    log.info("GET-request: getting uploaded document by id: {}", id);
    return dataService.findUploadedDocumentById(id);
  }

  /**
   * Deletes traffic data with the specified ID.
   *
   * @param id the ID of the traffic data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/traffic/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deleteTrafficById(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting traffic data by id: {}", id);
    dataService.deleteTrafficById(id);
  }

  /**
   * Deletes pedestrian and bicyclist data with the specified ID.
   *
   * @param id the ID of the pedestrian and bicyclist data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pedestrian-and-bicyclist/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deletePedestrianAndBicyclistById(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting pedestrian and bicyclist data by id: {}", id);
    dataService.deletePedestrianAndBicyclistById(id);
  }

  /**
   * Deletes crash data with the specified ID.
   *
   * @param id the ID of the crash data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/crash-data/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deleteCrashDataById(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting crash data by id: {}", id);
    dataService.deleteCrashDataById(id);
  }

  /**
   * Deletes traffic data with the specified document ID.
   *
   * @param id the document ID of the traffic data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/traffic/document/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deleteTrafficByDocumentId(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting traffic data by id: {}", id);
    dataService.deleteTrafficByDocumentId(id);
  }

  /**
   * Deletes pedestrian and bicyclist data with the specified document ID.
   *
   * @param id the document ID of the pedestrian and bicyclist data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/pedestrian-and-bicyclist/document/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deletePedestrianAndBicyclistByDocumentId(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting pedestrian and bicyclist data by document id: {}", id);
    dataService.deletePedestrianAndBicyclistByDocumentId(id);
  }

  /**
   * Deletes crash data with the specified document ID.
   *
   * @param id the document ID of the crash data to delete
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/crash-data/document/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public void deleteCrashDataByDocumentId(@PathVariable("id") Long id) {
    log.info("DELETE-request: deleting crash data by document id: {}", id);
    dataService.deleteCrashDataByDocumentId(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/csv")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UploadReportDto processCsv(@RequestParam("file") MultipartFile file) {
    log.info("POST-request: uploading document");
    return csvProcessingService.processCsvDocument(file);
  }
}

