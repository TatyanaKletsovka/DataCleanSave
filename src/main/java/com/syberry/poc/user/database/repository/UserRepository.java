package com.syberry.poc.user.database.repository;

import com.syberry.poc.exception.EntityNotFoundException;
import com.syberry.poc.user.database.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing users in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  /**
   * Returns whether a user with the specified email exists in the repository.
   *
   * @param email the email of the user to check for existence
   * @return true if a user with the specified email exists, false otherwise
   */
  boolean existsByEmail(String email);

  /**
   * Finds a user by their ID and throws an EntityNotFoundException if they do not exist.
   *
   * @param id the ID of the user to find
   * @return the user with the given ID
   * @throws EntityNotFoundException if the user does not exist
   */
  default User findByIdIfExists(Long id) {
    return findById(id).orElseThrow(()
        -> new EntityNotFoundException(String.format("User with id: %s is not found", id)));
  }

  /**
   * Finds an active user by email.
   *
   * @param email the unique email of the user to find
   * @return an Optional containing the user if it exists, or an empty Optional if it does not
   */
  Optional<User> findByEmailAndEnabledTrue(String email);

  /**
   * Finds a user by email and throws an EntityNotFoundException if is doesn't exist.
   *
   * @param email the email of the user to find
   * @return the user with the given email
   * @throws EntityNotFoundException if the user does not exist
   */
  default User findActiveUserByEmailIfExists(String email) {
    return findByEmailAndEnabledTrue(email).orElseThrow(()
        -> new EntityNotFoundException(
        String.format("User with email: %s is not found", email)));
  }
}
