package com.syberry.poc.data.dto.enums;

import com.syberry.poc.data.util.FileNameConstants;

/**
 * An enumeration of available document types to be proceeded.
 */
public enum DocumentType {
  PEDESTRIAN(FileNameConstants.pedestrianDocument),
  TRAFFIC(FileNameConstants.trafficDocument),
  CRASH(FileNameConstants.crashDocument);

  private final String type;

  DocumentType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
