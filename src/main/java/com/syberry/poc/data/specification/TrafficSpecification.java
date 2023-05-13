package com.syberry.poc.data.specification;

import com.syberry.poc.data.converter.EnumConverter;
import com.syberry.poc.data.database.entity.Document_;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.database.entity.Traffic_;
import com.syberry.poc.data.dto.TrafficFilter;
import com.syberry.poc.data.dto.enums.Direction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * A component that builds a Specification for querying the database
 * for Traffic entities based on various filtering criteria provided in a TrafficFilter object.
 */
@Component
@RequiredArgsConstructor
public class TrafficSpecification {

  /**
   * Builds a Specification for finding all Users that match the given filter.
   *
   * @param filter the filter to apply when searching for users
   * @return a Specification for finding all matching users
   */
  public Specification<Traffic> buildTrafficSpecification(TrafficFilter filter) {
    return buildCountyLikeSpecification(filter.getCounty())
        .and(buildCommunityLikeSpecification(filter.getCommunity()))
        .and(buildOnRoadLikeSpecification(filter.getOnRoad()))
        .and(buildFromRoadLikeSpecification(filter.getFromRoad()))
        .and(buildToRoadLikeSpecification(filter.getToRoad()))
        .and(buildApproachLikeSpecification(filter.getApproach()))
        .and(buildAtLikeSpecification(filter.getAt()))
        .and(buildDirectionEqualSpecification(
            EnumConverter.convertToEntity(filter.getDirection(), Direction.class)))
        .and(buildDirectionsLikeSpecification(filter.getDirections()))
        .and(buildDocumentIdEqualSpecification(filter.getDocumentId()));
  }

  /**
   * Builds a Specification for matching Traffic with county that contain the given string.
   *
   * @param county the county string to match against
   * @return a Specification for matching Traffic with county that contain the given string
   */
  private Specification<Traffic> buildCountyLikeSpecification(String county) {
    return (root, query, criteriaBuilder) ->
        county != null
        ? criteriaBuilder.like(
            root.get(Traffic_.COUNTY), "%" + county + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with community that contain the given string.
   *
   * @param community the community string to match against
   * @return a Specification for matching Traffic with community that contain the given string
   */
  private Specification<Traffic> buildCommunityLikeSpecification(String community) {
    return (root, query, criteriaBuilder) ->
        community != null
        ? criteriaBuilder.like(
            root.get(Traffic_.COMMUNITY), "%" + community + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with onRoad that contain the given string.
   *
   * @param onRoad the onRoad string to match against
   * @return a Specification for matching Traffic with onRoad that contain the given string
   */
  private Specification<Traffic> buildOnRoadLikeSpecification(String onRoad) {
    return (root, query, criteriaBuilder) ->
        onRoad != null
        ? criteriaBuilder.like(
            root.get(Traffic_.ON_ROAD), "%" + onRoad + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with fromRoad that contain the given string.
   *
   * @param fromRoad the fromRoad string to match against
   * @return a Specification for matching Traffic with fromRoad that contain the given string
   */
  private Specification<Traffic> buildFromRoadLikeSpecification(String fromRoad) {
    return (root, query, criteriaBuilder) ->
        fromRoad != null
        ? criteriaBuilder.like(
            root.get(Traffic_.FROM_ROAD), "%" + fromRoad + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with toRoad that contain the given string.
   *
   * @param toRoad the toRoad string to match against
   * @return a Specification for matching Traffic with toRoad that contain the given string
   */
  private Specification<Traffic> buildToRoadLikeSpecification(String toRoad) {
    return (root, query, criteriaBuilder) ->
        toRoad != null
        ? criteriaBuilder.like(
            root.get(Traffic_.TO_ROAD), "%" + toRoad + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with approach that contain the given string.
   *
   * @param approach the approach string to match against
   * @return a Specification for matching Traffic with approach that contain the given string
   */
  private Specification<Traffic> buildApproachLikeSpecification(String approach) {
    return (root, query, criteriaBuilder) ->
        approach != null
        ? criteriaBuilder.like(
            root.get(Traffic_.APPROACH), "%" + approach + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with at that contain the given string.
   *
   * @param at the at string to match against
   * @return a Specification for matching Traffic with at that contain the given string
   */
  private Specification<Traffic> buildAtLikeSpecification(String at) {
    return (root, query, criteriaBuilder) ->
        at != null
        ? criteriaBuilder.like(
            root.get(Traffic_.AT), "%" + at + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with a given direction.
   *
   * @param direction the direction to match against
   * @return a Specification for matching Traffic with a given direction
   */
  private Specification<Traffic> buildDirectionEqualSpecification(Direction direction) {
    return (root, query, criteriaBuilder) ->
        direction != null
        ? criteriaBuilder.equal(root.get(Traffic_.DIRECTION), direction)
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with directions that contain the given string.
   *
   * @param directions the directions string to match against
   * @return a Specification for matching Traffic with directions that contain the given string
   */
  private Specification<Traffic> buildDirectionsLikeSpecification(String directions) {
    return (root, query, criteriaBuilder) ->
        directions != null
        ? criteriaBuilder.like(
            root.get(Traffic_.DIRECTIONS), "%" + directions + "%")
        : null;
  }

  /**
   * Builds a Specification for matching Traffic with given documentId.
   *
   * @param documentId the documentId to match against
   * @return a Specification for matching Traffic with given documentId
   */
  private Specification<Traffic> buildDocumentIdEqualSpecification(Long documentId) {
    return (root, query, criteriaBuilder) -> documentId != null
        ? criteriaBuilder.equal(root.get(Traffic_.DOCUMENT).get(Document_.ID), documentId)
        : null;
  }
}
