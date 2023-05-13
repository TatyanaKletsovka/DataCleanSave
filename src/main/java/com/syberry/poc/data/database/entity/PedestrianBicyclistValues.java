package com.syberry.poc.data.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity representing pedestrian and bicyclist values in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedestrianBicyclistValues {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @NotNull
  private String columnName;
  private Long columnNameHash;
  @NotNull
  private String value;
  private Long valueHash;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "pedestrian_bicyclist_id", referencedColumnName = "id")
  private PedestrianBicyclist pedestrianBicyclist;
}

