package com.syberry.poc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Filter object representing fields to filter Traffic.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrafficFilter {

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
