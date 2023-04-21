package com.syberry.poc.user.service.impl;

import com.syberry.poc.user.converter.RoleConverter;
import com.syberry.poc.user.converter.UserConverter;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.PasswordUpdatingDto;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserFilter;
import com.syberry.poc.user.dto.UserUpdatingDto;
import com.syberry.poc.user.dto.enums.RoleName;
import com.syberry.poc.user.service.UserService;
import com.syberry.poc.user.specification.UserSpecification;
import com.syberry.poc.user.validation.UserValidator;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserConverter converter;
  private final RoleConverter roleConverter;
  private final UserRepository repository;
  private final UserValidator validator;
  private final UserSpecification specification;

  /**
   * Finds a page of users based on the filter and page parameters.
   *
   * @param filter The filter used to find users.
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of users represented as a DTO
   *     based on the provided filter and page parameters.
   */
  @Override
  public Page<UserDto> findAllUsers(UserFilter filter, Pageable pageable) {
    return repository.findAll(specification.buildGetAllSpecification(filter), pageable)
        .map(converter::convertToUserDto);
  }

  /**
   * Finds a user by their ID and returns it as a DTO.
   *
   * @param id the ID of the user to find
   * @return the user with the given ID, represented as a DTO
   */
  @Override
  public UserDto findUserById(Long id) {
    return converter.convertToUserDto(repository.findByIdIfExists(id));
  }

  /**
   * Finds the currently authenticated user's profile and returns it as a DTO.
   *
   * @return the currently authenticated user's profile, represented as a DTO
   */
  @Override
  public UserDto findUserProfile() {
    // TODO: add realization in authorization feature
    return null;
  }

  /**
   * Creates a new user from the given DTO and returns it as a DTO.
   *
   * @param dto the DTO representing the user to create
   * @return the created user, represented as a DTO
   */
  @Override
  public UserDto createUser(UserCreationDto dto) {
    validator.validateEmail(dto.getEmail());
    User user = converter.convertToEntity(dto);
    // TODO: add realization in authorization feature
    user.setPassword("password");
    return converter.convertToUserDto(repository.save(user));
  }

  /**
   * Updates a user by his ID and returns it as a DTO.
   *
   * @param dto the dto representing the user to update
   * @return the updated user, represented as a DTO
   */
  @Override
  @Transactional
  public UserDto updateUserById(UserUpdatingDto dto) {
    User user = repository.findByIdIfExists(dto.getId());
    validator.validateUpdating(user);
    user = converter.convertToEntity(dto, user);
    return converter.convertToUserDto(user);
  }

  /**
   * Disables or enables a user by their ID and returns it as a DTO.
   *
   * @param id the ID of the user to disable or enable
   * @return the updated user, represented as a DTO
   */
  @Override
  @Transactional
  public UserDto disableUserById(Long id) {
    User user = repository.findByIdIfExists(id);
    user.setDisabledAt(user.isEnabled() ? LocalDateTime.now() : null);
    user.setEnabled(!user.isEnabled());
    return converter.convertToUserDto(user);
  }

  /**
   * Change Admin role to User role and conversely.
   *
   * @param id the ID of the user to change role
   * @return the updated user, represented as a DTO
   */
  @Override
  @Transactional
  public UserDto switchAdminRoleById(Long id) {
    User user = repository.findByIdIfExists(id);
    validator.validateRole(user);
    validator.validateUpdating(user);
    user.setRole(
        user.getRole().getRoleName().equals(RoleName.USER)
        ? roleConverter.convertToEntity(RoleName.ADMIN)
        : roleConverter.convertToEntity(RoleName.USER));
    user.setUpdatedAt(LocalDateTime.now());
    return converter.convertToUserDto(user);
  }

  /**
   * Updates the password for the currently authenticated user.
   *
   * @param dto the dto to check and update password
   */
  @Override
  @Transactional
  public void updatePassword(PasswordUpdatingDto dto) {
    // TODO: add realization in authorization feature
  }
}
