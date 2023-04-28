package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents traffic entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDto {

  private Long id;
  private String county;
  private String community;
  private String onRoad;
  private String fromRoad;
  private String toRoad;
  private String approach;
  private String at;
  private String direction;
  private String directions;
  private Float latitude;
  private Float longitude;
  private Long documentId;
}
