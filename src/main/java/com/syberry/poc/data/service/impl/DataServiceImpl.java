package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.repository.CrashDataRepository;
import com.syberry.poc.data.database.repository.DocumentRepository;
import com.syberry.poc.data.database.repository.PedestrianBicyclistRepository;
import com.syberry.poc.data.database.repository.TrafficRepository;
import com.syberry.poc.data.dto.CrashDataDto;
import com.syberry.poc.data.dto.DocumentDto;
import com.syberry.poc.data.dto.PedestrianBicyclistDto;
import com.syberry.poc.data.dto.TrafficDto;
import com.syberry.poc.data.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Data.
 */

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

  private final DocumentRepository documentRepository;
  private final TrafficRepository trafficRepository;
  private final PedestrianBicyclistRepository pedestrianBicyclistRepository;
  private final CrashDataRepository crashDataRepository;
  private final DataConverter dataConverter;

  /**
   * Returns a page of traffic data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of TrafficDto objects representing the traffic data
   *     based on the provided page parameters.
   */
  @Override
  public Page<TrafficDto> findAllTraffic(Pageable pageable) {
    return trafficRepository.findAll(pageable)
        .map(dataConverter::convertToTrafficDto);
  }

  /**
   * Returns a page of pedestrian and bicyclist data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of PedestrianBicyclistDto objects representing the pedestrian and bicyclist
   *     data based on the provided page parameters.
   */
  @Override
  public Page<PedestrianBicyclistDto> findAllPedestrianAndBicyclist(Pageable pageable) {
    return pedestrianBicyclistRepository.findAll(pageable)
        .map(dataConverter::convertToPedestrianBicyclistDto);
  }

  /**
   * Returns a page of crash data based on page parameters.
   *
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of CrashDataDto objects representing the crash data
   *     based on the provided page parameters.
   */
  @Override
  public Page<CrashDataDto> findAllCrashData(Pageable pageable) {
    return crashDataRepository.findAll(pageable)
        .map(dataConverter::convertToCrashDataDto);
  }

  /**
   * Deletes traffic data with provided id from the repository.
   *
   * @param id the id of the traffic record to delete
   */
  @Override
  public void deleteTrafficById(Long id) {
    trafficRepository.findByIdIfExists(id);
    trafficRepository.deleteById(id);
  }

  /**
   * Deletes pedestrian and bicyclist data with provided id from the repository.
   *
   * @param id the id of the pedestrian and bicyclist record to delete
   */
  @Override
  public void deletePedestrianAndBicyclistById(Long id) {
    pedestrianBicyclistRepository.findByIdIfExists(id);
    pedestrianBicyclistRepository.deleteById(id);
  }

  /**
   * Deletes crash data with provided id from the repository.
   *
   * @param id the id of the crash data record to delete
   */
  @Override
  public void deleteCrashDataById(Long id) {
    crashDataRepository.findByIdIfExists(id);
    crashDataRepository.deleteById(id);
  }

  /**
   * Deletes traffic data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  @Override
  public void deleteTrafficByDocumentId(Long id) {
    trafficRepository.existsByDocumentIdOrThrow(id);
    trafficRepository.deleteByDocumentId(id);
  }

  /**
   * Deletes crash data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  @Override
  public void deleteCrashDataByDocumentId(Long id) {
    crashDataRepository.existsByDocumentIdOrThrow(id);
    crashDataRepository.deleteByDocumentId(id);
  }

  /**
   * Deletes pedestrian and bicyclist data with provided document id from the repository.
   *
   * @param id the id of the document to delete.
   */
  @Override
  public void deletePedestrianAndBicyclistByDocumentId(Long id) {
    pedestrianBicyclistRepository.existsByDocumentIdOrThrow(id);
    pedestrianBicyclistRepository.deleteByDocumentId(id);
  }

  /**
   * Returns an uploaded document based on document id.
   *
   * @param id the id of the document to delete.
   * @return dto the DocumentDto object to the given document id.
   */
  @Override
  public DocumentDto findUploadedDocumentById(Long id) {
    return dataConverter.convertToDocumentDto(documentRepository.findByIdIfExists(id));
  }
}
