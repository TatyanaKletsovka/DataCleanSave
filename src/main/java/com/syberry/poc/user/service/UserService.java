package com.syberry.poc.user.service;

import com.syberry.poc.user.dto.PasswordUpdatingDto;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserFilter;
import com.syberry.poc.user.dto.UserUpdatingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing users.
 */
public interface UserService {

  /**
   * Finds a page of users based on the filter and page parameters.
   *
   * @param filter The filter used to find users.
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of users represented as a DTO
   *     based on the provided filter and page parameters.
   */
  Page<UserDto> findAllUsers(UserFilter filter, Pageable pageable);

  /**
   * Finds a user by their ID and returns it as a DTO.
   *
   * @param id the ID of the user to find
   * @return the user with the given ID, represented as a DTO
   */
  UserDto findUserById(Long id);

  /**
   * Finds the currently authenticated user's profile and returns it as a DTO.
   *
   * @return the currently authenticated user's profile, represented as a DTO
   */
  UserDto findUserProfile();

  /**
   * Creates a new user from the given DTO and returns it as a DTO.
   *
   * @param dto the DTO representing the user to create
   * @return the created user, represented as a DTO
   */
  UserDto createUser(UserCreationDto dto);

  /**
   * Updates a user by his ID and returns it as a DTO.
   *
   * @param dto the dto representing the user to update
   * @return the updated user, represented as a DTO
   */
  UserDto updateUserById(UserUpdatingDto dto);

  /**
   * Disables or enables a user by their ID and returns it as a DTO.
   *
   * @param id the ID of the user to disable or enable
   * @return the updated user, represented as a DTO
   */
  UserDto disableUserById(Long id);

  /**
   * Change Admin role to User role and conversely.
   *
   * @param id the ID of the user to change role
   * @return the updated user, represented as a DTO
   */
  UserDto switchAdminRoleById(Long id);

  /**
   * Updates the password for the currently authenticated user.
   *
   * @param dto the dto to check and update password
   */
  void updatePassword(PasswordUpdatingDto dto);
}
