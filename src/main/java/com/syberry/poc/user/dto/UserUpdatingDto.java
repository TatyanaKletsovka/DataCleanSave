package com.syberry.poc.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents fields to update a user entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatingDto {

  private Long id;
  @NotEmpty
  @Size(max = 45)
  private String firstName;
  @NotEmpty
  @Size(max = 45)
  private String lastName;
}
