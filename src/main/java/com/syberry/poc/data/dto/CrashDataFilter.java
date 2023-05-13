package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Filter object representing fields to filter CrashData.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrashDataFilter {

  private Integer year;
  private Integer month;
  private Integer day;
  private Integer hour;
  private String weekend;
  private String collisionType;
  private String injuryType;
  private String primaryFactor;
  private String reportedLocation;
  private Long documentId;
}
