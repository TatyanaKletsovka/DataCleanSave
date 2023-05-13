package com.syberry.poc.data.database.repository;

import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.dto.enums.WeekDay;
import com.syberry.poc.exception.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing pedestrian and bicyclist data in the database.
 */
@Repository
public interface PedestrianBicyclistRepository extends JpaRepository<PedestrianBicyclist, Long>,
    JpaSpecificationExecutor<PedestrianBicyclist> {

  /**
   * Finds a pedestrianBicyclist data by its id
   * and throws an EntityNotFoundException if it does not exist.
   *
   * @param id the id of the pedestrianBicyclist data to find
   * @throws EntityNotFoundException if the pedestrianBicyclist data with this id does not exist
   */
  default void findByIdIfExists(Long id) {
    findById(id).orElseThrow(()
        -> new EntityNotFoundException(
        String.format("PedestrianBicyclist with id: %s is not found", id)));
  }

  /**
   * Checks if a pedestrianBicyclist data record exists in the system with the given document id
   * and throws an EntityNotFoundException if it does not exist.
   *
   * @param id the id of the document to find
   * @return true if a pedestrianBicyclist data with the specified document id exists,
   *     EntityNotFoundException otherwise
   * @throws EntityNotFoundException if the pedestrianBicyclist data with this id does not exist
   */

  default boolean existsByDocumentIdOrThrow(Long id) {
    if (!existsByDocumentId(id)) {
      throw new EntityNotFoundException(String.format(
          "PedestrianBicyclist with document id: %s is not found", id));
    }
    return true;
  }

  /**
   * Deletes pedestrianBicyclist data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  void deleteByDocumentId(Long id);

  /**
   * Returns whether a pedestrianBicyclist data with the specified document id
   * exists in the repository.
   *
   * @param id the id of the document to check for existence
   * @return true if a pedestrianBicyclist data with the specified document id exists,
   *     false otherwise
   */
  boolean existsByDocumentId(Long id);

  /**
   * Returns a top record found filtered by params.
   *
   * @param year a year field value.
   * @param month a month field value.
   * @param dayOfMonth a dayOfMonth field value.
   * @param weekDay a weekDay field value.
   * @return PedestrianBicyclist entity found.
   */
  Optional<PedestrianBicyclist> findTopByYearAndMonthAndDayAndWeekDayOrderByIdDesc(
      int year, int month, int dayOfMonth, WeekDay weekDay);
}
