package com.syberry.poc.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents a request to create a new user entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

  @NotEmpty
  @Size(max = 45)
  private String firstName;
  @NotEmpty
  @Size(max = 45)
  private String lastName;
  @Email
  @NotEmpty
  @Size(max = 50)
  private String email;
  @NotEmpty
  private String roleName;
}
