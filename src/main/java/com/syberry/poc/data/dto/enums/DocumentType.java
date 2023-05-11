package com.syberry.poc.data.dto.enums;

import com.syberry.poc.data.util.Constants;

/**
 * An enumeration of available document types to be proceeded.
 */
public enum DocumentType {
  PEDESTRIAN(Constants.pedestrianDocument),
  TRAFFIC(Constants.trafficDocument),
  CRASH(Constants.crashDocument);

  private final String type;

  DocumentType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
