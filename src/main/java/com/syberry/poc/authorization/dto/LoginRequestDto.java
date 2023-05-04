package com.syberry.poc.authorization.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object which represents request to log in.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

  @NotEmpty
  @Size(max = 50)
  private String email;
  @NotEmpty
  private String password;
}
