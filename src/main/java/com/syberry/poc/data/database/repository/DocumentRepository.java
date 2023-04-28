package com.syberry.poc.data.database.repository;

import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing documents in the database.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

  /**
   * Finds a document by its id and throws an EntityNotFoundException if it does not exist.
   *
   * @param id the id of the document to find
   * @return the document if it exists
   * @throws EntityNotFoundException if the document with this id does not exist
   */
  default Document findByIdIfExists(Long id) {
    return findById(id).orElseThrow(()
        -> new EntityNotFoundException(
        String.format("Document with id: %s is not found", id)));
  }
}
