package com.syberry.poc.authorization.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class containing reusable constant fields using for working with user entity.
 */
@UtilityClass
public class Constants {

  public static final String USER_PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%^&+=]).{7,}$";
  public static final String USER_PASSWORD_MESSAGE =
      "User password must be at least 7 characters long, contain lower, upper,"
          + " not alphabetic symbols.";
}
