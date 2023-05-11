package com.syberry.poc.data.database.repository;

import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing crash data in the database.
 */
@Repository
public interface CrashDataRepository extends JpaRepository<CrashData, Long>,
    JpaSpecificationExecutor<CrashData> {

  /**
   * Finds a crash data by its id and throws an EntityNotFoundException if it does not exist.
   *
   * @param id the id of the crash data to find
   * @throws EntityNotFoundException if the crash data with this id does not exist
   */
  default void findByIdIfExists(Long id) {
    findById(id).orElseThrow(()
        -> new EntityNotFoundException(
        String.format("CrashData with id: %s is not found", id)));
  }

  /**
   * Checks if a crash data record exists in the system with the given document id
   * and throws an EntityNotFoundException if it does not exist.
   *
   * @param id the id of the document to find
   * @return true if a crash data with the specified document id exists,
   *     EntityNotFoundException otherwise
   * @throws EntityNotFoundException if the crash data with this id does not exist
   */
  default boolean existsByDocumentIdOrThrow(Long id) {
    if (!existsByDocumentId(id)) {
      throw new EntityNotFoundException(
          String.format("Traffic with document id: %s is not found", id));
    }
    return true;
  }

  /**
   * Deletes crash data with provided document id from the repository.
   *
   * @param id the id of the document to delete
   */
  void deleteByDocumentId(Long id);

  /**
   * Returns whether a crash data with the specified document id exists in the repository.
   *
   * @param id the id of the document to check for existence
   * @return true if a crash data with the specified document id exists, false otherwise
   */
  boolean existsByDocumentId(Long id);
}

