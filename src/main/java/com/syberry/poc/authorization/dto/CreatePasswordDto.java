package com.syberry.poc.authorization.dto;

import com.syberry.poc.authorization.util.Constants;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This object contains information for setting a new password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordDto {
  @Email
  @NotEmpty
  private String email;
  @NotEmpty
  private String token;
  @NotEmpty
  @Pattern(regexp = Constants.USER_PASSWORD_REGEX,
      message = Constants.USER_PASSWORD_MESSAGE)
  private String newPassword;
  @NotEmpty
  @Pattern(regexp = Constants.USER_PASSWORD_REGEX,
      message = Constants.USER_PASSWORD_MESSAGE)
  private String repeatPassword;
}
