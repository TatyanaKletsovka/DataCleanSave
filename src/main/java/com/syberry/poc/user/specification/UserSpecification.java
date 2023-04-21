package com.syberry.poc.user.specification;

import com.syberry.poc.user.converter.RoleConverter;
import com.syberry.poc.user.database.entity.Role_;
import com.syberry.poc.user.database.entity.User;
import com.syberry.poc.user.database.entity.User_;
import com.syberry.poc.user.dto.UserFilter;
import com.syberry.poc.user.dto.enums.RoleName;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * A component that builds a Specification for querying the database
 * for User entities based on various filtering criteria provided in a UserFilter object.
 */
@Component
@RequiredArgsConstructor
public class UserSpecification {

  private final RoleConverter roleConverter;

  /**
   * Builds a Specification for finding all Users that match the given filter.
   *
   * @param filter the filter to apply when searching for users
   * @return a Specification for finding all matching users
   */
  public Specification<User> buildGetAllSpecification(UserFilter filter) {
    return buildNameLikeSpecification(filter.getName())
        .and(buildEmailLikeSpecification(filter.getEmail()))
        .and(buildWhereRoleIsSpecification(filter.getRoleName()))
        .and(buildCreatedAtBetweenSpecification(filter.getCreatedAtStart().atStartOfDay(),
            filter.getCreatedAtEnd().plusDays(1).atStartOfDay().minusNanos(1)))
        .and(buildWhereEnabledIsSpecification(filter.getEnabled()));
  }

  /**
   * Builds a Specification for matching users with names that contain the given string.
   *
   * @param name the name string to match against
   * @return a Specification for matching users with names that contain the given string
   */
  private Specification<User> buildNameLikeSpecification(String name) {
    return (root, query, criteriaBuilder) ->
        name != null
        ? criteriaBuilder.like(
            criteriaBuilder.concat(
                criteriaBuilder.concat(
                    root.get(User_.FIRST_NAME), " "),
                root.get(User_.LAST_NAME)),
            "%" + name + "%")
        : null;
  }

  /**
   * Builds a Specification for matching users with email addresses that contain the given string.
   *
   * @param email the email string to match against
   * @return a Specification for matching users with email addresses that contain the given string
   */
  private Specification<User> buildEmailLikeSpecification(String email) {
    return (root, query, criteriaBuilder) ->
        email != null
        ? criteriaBuilder.like(
            root.get(User_.EMAIL), "%" + email + "%")
        : null;
  }

  /**
   * Builds a Specification for matching users with a given role name.
   *
   * @param role the name of the role to match against
   * @return a Specification for matching users with a given role name
   */
  private Specification<User> buildWhereRoleIsSpecification(String role) {
    RoleName roleName = role != null
        ? roleConverter.convertToEntity(role).getRoleName()
        : null;
    return (root, query, criteriaBuilder) -> roleName != null
        ? criteriaBuilder.equal(root.get(User_.ROLE).get(Role_.ROLE_NAME), roleName)
        : null;
  }

  /**
   * Builds a Specification for matching users with creation dates
   * between the given start and end times.
   *
   * @param createdAtStart the start of the creation date range
   * @param createdAtEnd the end of the creation date range
   * @return a Specification for matching users with creation dates
   *     between the given start and end times
   */
  private Specification<User> buildCreatedAtBetweenSpecification(LocalDateTime createdAtStart,
                                                                 LocalDateTime createdAtEnd) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.between(root.get(User_.CREATED_AT), createdAtStart, createdAtEnd);
  }

  /**
   * Builds a Specification for matching users with the given enabled status.
   *
   * @param enabled the enabled status to match against
   * @return a Specification for matching users with the given enabled status
   */
  private Specification<User> buildWhereEnabledIsSpecification(Boolean enabled) {
    return (root, query, criteriaBuilder) -> enabled != null
        ? criteriaBuilder.equal(root.get(User_.ENABLED), enabled)
        : null;
  }

}
