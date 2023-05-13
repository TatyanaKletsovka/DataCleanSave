package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents PedestrianBicyclist entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedestrianBicyclistFilter {

  private Integer year;
  private Integer month;
  private Integer day;
  private String weekDay;
  private Long documentId;
}
