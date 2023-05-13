package com.syberry.poc.data.dto.enums;

import java.util.List;

/**
 * An enumeration of processable document's columns and their tags.
 */
public enum Column {
  INJURY_TYPE(List.of(new Tag[]{Tag.STRING})),
  PRIMARY_FACTOR(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  REPORTED_LOCATION(List.of(new Tag[]{Tag.STRING})),
  LATITUDE(List.of(new Tag[]{Tag.FLOAT})),
  LONGITUDE(List.of(new Tag[]{Tag.FLOAT})),
  FULL_DATE(List.of(new Tag[]{Tag.FULL_DATE})),
  DATE(List.of(new Tag[]{Tag.DATE})),
  WEEKEND(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  HOUR(List.of(new Tag[]{Tag.INT, Tag.TIME})),
  COLLISION_DATE(List.of(new Tag[]{Tag.STRING, Tag.DATE})),
  COLLISION_TIME(List.of(new Tag[]{Tag.STRING, Tag.TIME})),
  COUNTY(List.of(new Tag[]{Tag.STRING})),
  COMMUNITY(List.of(new Tag[]{Tag.STRING})),
  ON(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  FROM(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  TO(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  APPROACH(List.of(new Tag[]{Tag.STRING})),
  AT(List.of(new Tag[]{Tag.STRING})),
  DIR(List.of(new Tag[]{Tag.STRING, Tag.OBLIGATORY})),
  DIRECTIONS(List.of(new Tag[]{Tag.STRING})),
  COLUMN_NAME(List.of(new Tag[]{Tag.STRING})),
  COLUMN_VALUE(List.of(new Tag[]{Tag.OBLIGATORY, Tag.INT})),
  COUNT(List.of(new Tag[]{Tag.INT, Tag.OBLIGATORY}));

  public final List<Tag> tags;

  Column(List<Tag> tags) {
    this.tags = tags;
  }
}
