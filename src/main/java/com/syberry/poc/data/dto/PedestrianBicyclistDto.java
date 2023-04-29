package com.syberry.poc.data.dto;

import java.util.List;
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
public class PedestrianBicyclistDto {

  private Long id;
  private int year;
  private int month;
  private int day;
  private String weekDay;
  private List<PedestrianBicyclistValuesDto> listValues;
  private Long documentId;
}
