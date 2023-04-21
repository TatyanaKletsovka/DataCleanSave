package com.syberry.poc.user.database.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The entity representing a user in the application.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(length = 45, nullable = false)
  private String firstName;
  @Column(length = 45, nullable = false)
  private String lastName;
  @Email
  @Column(length = 50, unique = true, nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  @NotNull
  private Role role;
  @NotNull
  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;
  private LocalDateTime disabledAt;
  @Builder.Default
  private boolean enabled = false;
}
