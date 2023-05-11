package com.syberry.poc.data.database.entity;

import com.syberry.poc.data.dto.enums.Direction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity representing traffic data in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @NotNull
  private String county;
  private Long countyHash;
  @NotNull
  private String community;
  private Long communityHash;
  @NotNull
  private String onRoad;
  private Long onRoadHash;
  private String fromRoad;
  private Long fromRoadHash;
  private String toRoad;
  private Long toRoadHash;
  private String approach;
  private Long approachHash;
  private String at;
  private Long atHash;
  @NotNull
  @Enumerated(EnumType.STRING)
  private Direction direction;
  private String directions;
  private Long directionsHash;
  private Float latitude;
  private Float longitude;
  @NotNull
  @OneToOne
  @JoinColumn(name = "document_id", referencedColumnName = "id")
  private Document document;
}

