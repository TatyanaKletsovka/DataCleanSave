package com.syberry.poc.data.converter;

import com.syberry.poc.exception.InvalidArgumentTypeException;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

/**
 * Utility class for converting string values to enumeration instances
 * and retrieving the names of enumeration constants.
 */
@UtilityClass
public class EnumConverter {

  /**
   * Converts a string value to an instance of the specified enumeration.
   *
   * @param value      the string value to convert to an enumeration
   * @param enumClass  the class of the enumeration
   * @param <T>        the enumeration type
   * @return an instance of the enumeration corresponding to the given value
   * @throws InvalidArgumentTypeException if the specified value is invalid
   *     for the given enumeration
   */
  public <T extends Enum<T>> T convertToEntity(String value, Class<T> enumClass) {
    try {
      return value != null ? Enum.valueOf(enumClass, value.toUpperCase()) : null;
    } catch (IllegalArgumentException e) {
      String validValues = getNames(enumClass);
      throw new InvalidArgumentTypeException(
          String.format("Error while converting invalid value: %s. Valid values: %s",
              value, validValues));
    }
  }

  /**
   * Retrieves the names of the constants in the specified enumeration class.
   *
   * @param enumClass the class of the enumeration
   * @param <T>       the enumeration type
   * @return a string containing the names of the enumeration constants, separated by commas
   */
  private <T extends Enum<T>> String getNames(Class<T> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.joining(", "));
  }
}
