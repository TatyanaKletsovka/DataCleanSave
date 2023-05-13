package com.syberry.poc.data.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.experimental.UtilityClass;

/**
 * Utility class for calculating hash.
 */
@UtilityClass
public class HashCalculator {

  /**
   * Calculates the hash value of a given string using the SHA-256 algorithm.
   *
   * @param string the input string to calculate the hash value for
   * @return the calculated hash value as a Long
   */
  public static Long calculateHash(String string) {
    if (string == null) {
      return null;
    }
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedString = string.getBytes(StandardCharsets.UTF_8);
      byte[] hashBytes = digest.digest(encodedString);
      long hash = 0;
      for (int i = 0; i < 8; i++) {
        hash <<= 8;
        hash |= (hashBytes[i] & 0xFF);
      }
      return hash;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
