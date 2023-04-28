package com.syberry.poc.data.database.entity;

import com.syberry.poc.user.database.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * The entity representing document in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document implements DataEntityInterface {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull
  private Long id;
  @NotNull
  private LocalDateTime dateTime;
  @NotNull
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  @NotNull
  private int processedRows;
}
