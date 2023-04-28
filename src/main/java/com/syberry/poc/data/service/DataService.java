package com.syberry.poc.data.service;

import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.TrafficDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing data.
 */

public interface DataService {

  /**
   * Returns a page of traffic data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of TrafficDto objects representing the traffic data
   *     based on the provided page parameters.
   */
  Page<TrafficDto> findAllTraffic(Pageable pageable);

  /**
   * Returns a page of pedestrian and bicyclist data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of PedestrianBicyclistDto objects representing
   *     the pedestrian and bicyclist data based on the provided page parameters.
   */
  Page<PedestrianBicyclistDto> findAllPedestrianAndBicyclist(Pageable pageable);

  /**
   * Returns a page of crash data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of CrashDataDto objects representing the crash data
   *     based on the provided page parameters.
   */
  Page<CrashDataDto> findAllCrashData(Pageable pageable);

  /**
   * Deletes traffic data with provided id from the repository.
   *
   * @param id the id of the traffic record to delete
   */
  void deleteTrafficById(Long id);

  /**
   * Deletes pedestrian and bicyclist data with provided id from the repository.
   *
   * @param id the id of the pedestrian and bicyclist record to delete
   */
  void deletePedestrianAndBicyclistById(Long id);

  /**
   * Deletes crash data with provided id from the repository.
   *
   * @param id the id of the crash data record to delete
   */
  void deleteCrashDataById(Long id);

  /**
   * Deletes traffic data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  void deleteTrafficByDocumentId(Long id);

  /**
   * Deletes crash data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  void deleteCrashDataByDocumentId(Long id);

  /**
   * Deletes pedestrian and bicyclist data with provided document id from the repository.
   *
   * @param id the id of the document to delete.
   */
  void deletePedestrianAndBicyclistByDocumentId(Long id);

  /**
   * Returns an uploaded document based on document id.
   *
   * @param id the id of the document to delete.
   * @return dto the DocumentDto object to the given document id.
   */
  DocumentDto findUploadedDocumentById(Long id);
}
