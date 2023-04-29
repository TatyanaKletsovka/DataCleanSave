package com.syberry.poc.data.database.entity;

import com.syberry.poc.data.dto.enums.InjuryType;
import com.syberry.poc.data.dto.enums.Weekend;
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
 * The entity representing crash data in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrashData implements DataEntityInterface {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull
  private Long id;
  @NotNull
  private int year;
  @NotNull
  private int month;
  @NotNull
  private int day;
  @NotNull
  private int hour;
  @Enumerated(EnumType.STRING)
  private Weekend weekend;
  @NotNull
  private String collisionType;
  private Long collisionTypeHash;
  @NotNull
  @Enumerated(EnumType.STRING)
  private InjuryType injuryType;
  private String primaryFactor;
  private Long primaryFactorHash;
  private String reportedLocation;
  private Long reportedLocationHash;
  private Float latitude;
  private Float longitude;
  @NotNull
  @OneToOne
  @JoinColumn(name = "document_id", referencedColumnName = "id")
  private Document document;
}

