package com.syberry.poc.authorization.dto;

import com.syberry.poc.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object that transfer cookies and user information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

  private String cookie;
  private String refreshCookie;
  private UserDto userDto;
}
