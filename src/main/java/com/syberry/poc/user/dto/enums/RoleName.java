package com.syberry.poc.user.dto.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An enumeration of available roles in the application.
 */
public enum RoleName {

  SUPER_ADMIN,
  ADMIN,
  USER;

  /**
   * Returns a string containing the names of valid role names.
   *
   * @return a string containing the names of valid role names
   */
  public static String getNames() {
    return Arrays.stream(RoleName.class.getEnumConstants())
        .filter(roleName -> roleName != RoleName.SUPER_ADMIN)
        .map(Enum::name)
        .collect(Collectors.joining(", "));
  }
}
