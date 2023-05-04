package com.syberry.poc.user.dto;

import com.syberry.poc.authorization.util.Constants;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
  @Pattern(regexp = Constants.USER_PASSWORD_REGEX,
      message = Constants.USER_PASSWORD_MESSAGE)
  private String newPassword;
}
