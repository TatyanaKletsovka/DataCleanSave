package com.syberry.poc.data.specification;

import com.syberry.poc.data.converter.EnumConverter;
import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.CrashData_;
import com.syberry.poc.data.database.entity.Document_;
import com.syberry.poc.data.dto.CrashDataFilter;
import com.syberry.poc.data.dto.enums.InjuryType;
import com.syberry.poc.data.dto.enums.Weekend;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * A component that builds a Specification for querying the database for CrashData entities
 * based on various filtering criteria provided in a CrashDataFilter object.
 */
@Component
@RequiredArgsConstructor
public class CrashDataSpecification {

  /**
   * Builds a Specification for finding all Users that match the given filter.
   *
   * @param filter the filter to apply when searching for users
   * @return a Specification for finding all matching users
   */
  public Specification<CrashData> buildCrashDataSpecification(CrashDataFilter filter) {
    return buildYearEqualSpecification(filter.getYear())
        .and(buildMonthEqualSpecification(filter.getMonth()))
        .and(buildDayEqualSpecification(filter.getDay()))
        .and(buildHourEqualSpecification(filter.getHour()))
        .and(buildWeekendEqualSpecification(
            EnumConverter.convertToEntity(filter.getWeekend(), Weekend.class)))
        .and(buildCollisionTypeLikeSpecification(filter.getCollisionType()))
        .and(buildInjuryTypeEqualSpecification(
            EnumConverter.convertToEntity(filter.getInjuryType(), InjuryType.class)))
        .and(buildPrimaryFactorLikeSpecification(filter.getPrimaryFactor()))
        .and(buildReportedLocationLikeSpecification(filter.getReportedLocation()))
        .and(buildDocumentIdEqualSpecification(filter.getDocumentId()));
  }

  /**
   * Builds a Specification for matching CrashData with a given year.
   *
   * @param year the year to match against
   * @return a Specification for matching CrashData with a given year
   */
  private Specification<CrashData> buildYearEqualSpecification(Integer year) {
    return (root, query, criteriaBuilder) ->
        year != null
            ? criteriaBuilder.equal(root.get(CrashData_.YEAR), year)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData with a given month.
   *
   * @param month the month to match against
   * @return a Specification for matching CrashData with a given month
   */
  private Specification<CrashData> buildMonthEqualSpecification(Integer month) {
    return (root, query, criteriaBuilder) ->
        month != null
            ? criteriaBuilder.equal(root.get(CrashData_.MONTH), month)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData with a given day.
   *
   * @param day the day to match against
   * @return a Specification for matching CrashData with a given day
   */
  private Specification<CrashData> buildDayEqualSpecification(Integer day) {
    return (root, query, criteriaBuilder) ->
        day != null
            ? criteriaBuilder.equal(root.get(CrashData_.DAY), day)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData with a given hour.
   *
   * @param hour the hour to match against
   * @return a Specification for matching CrashData with a given hour
   */
  private Specification<CrashData> buildHourEqualSpecification(Integer hour) {
    return (root, query, criteriaBuilder) ->
        hour != null
            ? criteriaBuilder.equal(root.get(CrashData_.HOUR), hour)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData with a given weekend.
   *
   * @param weekend the weekend to match against
   * @return a Specification for matching CrashData with a given weekend
   */
  private Specification<CrashData> buildWeekendEqualSpecification(Weekend weekend) {
    return (root, query, criteriaBuilder) ->
        weekend != null
            ? criteriaBuilder.equal(root.get(CrashData_.WEEKEND), weekend)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData
   * with collisionType that contain the given string.
   *
   * @param collisionType the collisionType string to match against
   * @return a Specification for matching CrashData
   *     with collisionType that contain the given string
   */
  private Specification<CrashData> buildCollisionTypeLikeSpecification(String collisionType) {
    return (root, query, criteriaBuilder) ->
        collisionType != null
        ? criteriaBuilder.like(
            root.get(CrashData_.COLLISION_TYPE), "%" + collisionType + "%")
        : null;
  }

  /**
   * Builds a Specification for matching CrashData with a given injuryType.
   *
   * @param injuryType the injuryType to match against
   * @return a Specification for matching CrashData with a given injuryType
   */
  private Specification<CrashData> buildInjuryTypeEqualSpecification(InjuryType injuryType) {
    return (root, query, criteriaBuilder) ->
        injuryType != null
            ? criteriaBuilder.equal(root.get(CrashData_.INJURY_TYPE), injuryType)
            : null;
  }

  /**
   * Builds a Specification for matching CrashData
   * with primaryFactor that contain the given string.
   *
   * @param primaryFactor the primaryFactor string to match against
   * @return a Specification for matching CrashData
   *     with primaryFactor that contain the given string
   */
  private Specification<CrashData> buildPrimaryFactorLikeSpecification(String primaryFactor) {
    return (root, query, criteriaBuilder) ->
        primaryFactor != null
        ? criteriaBuilder.like(
            root.get(CrashData_.PRIMARY_FACTOR), "%" + primaryFactor + "%")
        : null;
  }

  /**
   * Builds a Specification for matching CrashData
   * with reportedLocation that contain the given string.
   *
   * @param reportedLocation the reportedLocation string to match against
   * @return a Specification for matching CrashData
   *     with reportedLocation that contain the given string
   */
  private Specification<CrashData> buildReportedLocationLikeSpecification(
      String reportedLocation) {
    return (root, query, criteriaBuilder) ->
        reportedLocation != null
        ? criteriaBuilder.like(
            root.get(CrashData_.REPORTED_LOCATION), "%" + reportedLocation + "%")
        : null;
  }

  /**
   * Builds a Specification for matching CrashData with given documentId.
   *
   * @param documentId the documentId to match against
   * @return a Specification for matching CrashData with given documentId
   */
  private Specification<CrashData> buildDocumentIdEqualSpecification(Long documentId) {
    return (root, query, criteriaBuilder) -> documentId != null
        ? criteriaBuilder.equal(root.get(CrashData_.DOCUMENT).get(Document_.ID), documentId)
        : null;
  }
}
