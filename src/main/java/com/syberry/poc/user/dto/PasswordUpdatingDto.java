package com.syberry.poc.user.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data transfer object that represents passwords to update user password.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdatingDto {

  @NotEmpty
  private String oldPassword;
  @NotEmpty
  private String newPassword;
}
