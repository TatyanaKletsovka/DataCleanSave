package com.syberry.poc.user.converter;

import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserUpdatingDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A component that provides converting methods for the User entity.
 */
@Component
@RequiredArgsConstructor
public class UserConverter {

  private final RoleConverter converter;

  /**
   * Converts a UserCreationDto object to a User entity.
   *
   * @param dto the UserCreationDto object to be converted.
   * @return the User entity corresponding to the given dto.
   */
  public User convertToEntity(UserCreationDto dto) {
    return User.builder()
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .email(dto.getEmail())
        .role(converter.convertToEntity(dto.getRoleName()))
        .build();
  }

  /**
   * Updates a User entity with a UserUpdatingDto object.
   *
   * @param dto the UserUpdatingDto object to be converted.
   * @param user the User object to be updated.
   * @return the User entity corresponding to the given dto.
   */
  public User convertToEntity(UserUpdatingDto dto, User user) {
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setUpdatedAt(LocalDateTime.now());
    return user;
  }

  /**
   * Converts a User entity to a UserDto object.
   *
   * @param user the User entity to be converted.
   * @return the UserDto object corresponding to the given user.
   */
  public UserDto convertToUserDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .roleName(user.getRole().getRoleName())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .disabledAt(user.getDisabledAt())
        .enabled(user.isEnabled())
        .build();
  }
}
