package com.syberry.poc.user.validation;

import com.syberry.poc.exception.ValidationException;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.repository.UserRepository;
import com.syberry.poc.user.dto.PasswordUpdatingDto;
import com.syberry.poc.user.dto.enums.RoleName;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service class for validating input related to users.
 */
@Service
@RequiredArgsConstructor
public class UserValidator {

  private final UserRepository userRepository;

  /**
   * Validates if the given email is already taken
   * by another user and throws a ValidationException if it is.
   *
   * @param email the email to validate
   * @throws ValidationException if the email is already taken by another user
   */
  public void validateEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new ValidationException(
          String.format("Email: %s is already taken. Try another one", email));
    }
  }

  /**
   * Validates if the user is enabled and throws a ValidationException if it is not.
   *
   * @param user the user to validate
   * @throws ValidationException if the user is disabled
   */
  public void validateUpdating(User user) {
    if (!user.isEnabled()) {
      throw new ValidationException("Disabled user can't be updated");
    }
  }

  /**
   * Validates if the user is SUPER_ADMIN and throws a ValidationException if it is.
   *
   * @param user the user to validate
   * @throws ValidationException if the user is SUPER_ADMIN
   */
  public void validateRole(User user) {
    if (user.getRole().getRoleName().equals(RoleName.SUPER_ADMIN)) {
      throw new ValidationException("SUPER_ADMIN role can't be changed");
    }
  }

  /**
   * Validates if passwords are not equals
   * and oldPassword are equals user password.
   *
   * @param user the user to validate
   * @param dto passwords to validate
   * @throws ValidationException if passwords are equals
   * @throws ValidationException if oldPassword are not equals user password
   */
  public void validatePassword(User user, PasswordUpdatingDto dto) {
    if (Objects.equals(dto.getOldPassword(), dto.getNewPassword())) {
      throw new ValidationException("New password shouldn't be the same as an old password");
    }
    if (!Objects.equals(user.getPassword(), dto.getOldPassword())) {
      throw new ValidationException("Old password is incorrect");
    }
  }
}
