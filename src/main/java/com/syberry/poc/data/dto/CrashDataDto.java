package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents crash data entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrashDataDto {

  private Long id;
  private int year;
  private int month;
  private int day;
  private int hour;
  private String weekend;
  private String collisionType;
  private String injuryType;
  private String primaryFactor;
  private String reportedLocation;
  private Float latitude;
  private Float longitude;
  private Long documentId;
}
