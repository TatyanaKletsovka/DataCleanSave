package com.syberry.poc.user.controller;

import com.syberry.poc.user.dto.PasswordUpdatingDto;
import com.syberry.poc.user.dto.UserCreationDto;
import com.syberry.poc.user.dto.UserDto;
import com.syberry.poc.user.dto.UserFilter;
import com.syberry.poc.user.dto.UserUpdatingDto;
import com.syberry.poc.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for handling user-related HTTP requests.
 */
@RestController
@CrossOrigin
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  /**
   * Returns a page of users based on the filter and page parameters.
   *
   * @param filter   The filter used to find users.
   * @param pageable The page parameters used to retrieve a specific page of results.
   * @return A Page of UserDto objects representing the users
   *     based on the provided filter and page parameters.
   */
  @GetMapping
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public Page<UserDto> findAllUsers(UserFilter filter, Pageable pageable) {
    log.info("GET-request: getting all users");
    return service.findAllUsers(filter, pageable);
  }

  /**
   * Returns the user with the specified ID.
   *
   * @param id the ID of the user to return
   * @return the user with the specified ID
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UserDto findUserById(@PathVariable("id") Long id) {
    log.info("GET-request: getting user with id: {}", id);
    return service.findUserById(id);
  }

  /**
   * Returns the profile of the current user.
   *
   * @return the profile of the current user
   */
  @GetMapping("/profile")
  public UserDto findUserProfile() {
    log.info("GET-request: getting current user profile");
    return service.findUserProfile();
  }

  /**
   * Creates a new user with the specified data.
   *
   * @param dto the data of the user to create
   * @return the created user
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UserDto createUser(@Valid @RequestBody UserCreationDto dto) {
    log.info("POST-request: creating new user");
    return service.createUser(dto);
  }

  /**
   * Updates the user with the specified ID.
   *
   * @param id  the ID of the user to update
   * @param dto the new data of the user
   * @return the updated user
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UserDto updateUserById(@PathVariable("id") Long id,
                                @Valid @RequestBody UserUpdatingDto dto) {
    log.info("PUT-request: updating user with id: {}", id);
    dto.setId(id);
    return service.updateUserById(dto);
  }

  /**
   * Reverses the disabled status of the user with the specified ID.
   *
   * @param id the ID of the user to update
   * @return the updated user
   */
  @PutMapping("/{id}/disabled")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UserDto disableUserById(@PathVariable("id") Long id) {
    log.info("PUT-request: reverse is user disabled for user with id: {}", id);
    return service.disableUserById(id);
  }

  /**
   * Switches the admin role of the user with the specified ID.
   *
   * @param id the ID of the user to update
   * @return the updated user
   */
  @PutMapping("/{id}/admin")
  @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
  public UserDto switchAdminRoleById(@PathVariable("id") Long id) {
    log.info("PUT-request: reverse admin role for user with id: {}", id);
    return service.switchAdminRoleById(id);
  }

  /**
   * Updates the password of the user with the specified ID.
   *
   * @param dto the dto with new and old passwords of the user
   */
  @PutMapping("/new-password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePassword(@Valid @RequestBody PasswordUpdatingDto dto) {
    log.info("PUT-request: updating password for authorized user");
    service.updatePassword(dto);
  }
}
