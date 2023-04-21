package com.syberry.poc.user.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Filter object representing fields to filter by user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

  private String name;
  private String email;
  private String roleName;
  private LocalDate createdAtStart = LocalDate.of(2000, 1, 1);
  private LocalDate createdAtEnd = LocalDate.of(3000, 1, 1);
  private Boolean enabled;
}
