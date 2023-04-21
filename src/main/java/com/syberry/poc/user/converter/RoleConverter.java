package com.syberry.poc.user.converter;

import com.syberry.poc.exception.InvalidArgumentTypeException;
import com.syberry.poc.user.database.entity.Role;
import com.syberry.poc.user.database.repository.RoleRepository;
import com.syberry.poc.user.dto.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A component that provides converting methods for the Role entity.
 */
@Component
@RequiredArgsConstructor
public class RoleConverter {

  private final RoleRepository repository;

  /**
   * Converts a RoleName object to a Role entity from repository.
   *
   * @param roleName the RoleName object to be converted.
   * @return the Role entity corresponding to the given roleName.
   */
  public Role convertToEntity(RoleName roleName) {
    return repository.findByRoleNameIfExists(roleName);
  }

  /**
   * Converts a role name string to a Role entity from repository.
   *
   * @param role the role name string to be converted.
   * @return the Role entity corresponding to the given role name.
   * @throws InvalidArgumentTypeException if the given role name is invalid.
   */
  public Role convertToEntity(String role) {
    try {
      return repository.findByRoleNameIfExists(RoleName.valueOf(role.toUpperCase()));
    } catch (IllegalArgumentException e) {
      throw new InvalidArgumentTypeException(
          String.format("Error while converting invalid role: %s. Valid roles: %s",
              role, RoleName.getNames()));
    }
  }
}
