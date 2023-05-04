package com.syberry.poc.data.database.entity;

import com.syberry.poc.data.dto.enums.WeekDay;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity representing pedestrian and bicyclist data in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedestrianBicyclist implements DataEntityInterface {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
  @Enumerated(EnumType.STRING)
  private WeekDay weekDay;
  @NotNull
  @OneToOne
  @JoinColumn(name = "document_id", referencedColumnName = "id")
  private Document document;
  @OneToMany
  @JoinColumn(name = "pedestrian_bicyclist_id")
  private List<PedestrianBicyclistValues> values;
}
