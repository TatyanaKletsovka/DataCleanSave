package com.syberry.poc.data.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class containing reusable constant fields with patterns.
 */
@UtilityClass
public class PatternConstants {
  public static final String REPLACEMENT_PATTERN = "[-/\\s]+";
  public static final String SPACES_PATTERN = "\\s+";
  public static final String HEADER_REPLACEMENT_PATTERN = "[^\\w_]";
  public static final String REPLACE_WITH_PATTERN = "_";
  public static final String REPLACE_CLEAN_PATTERN = "";
  public static final String NUMERIC_PATTERN = "\\d+";
}
