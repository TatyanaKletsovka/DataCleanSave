package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents PedestrianBicyclistValues entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedestrianBicyclistValuesDto {

  private Long id;
  private String columnName;
  private String value;
}
