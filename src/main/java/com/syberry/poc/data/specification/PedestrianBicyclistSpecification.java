package com.syberry.poc.data.specification;

import com.syberry.poc.data.converter.EnumConverter;
import com.syberry.poc.data.database.entity.Document_;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclist_;
import com.syberry.poc.data.dto.PedestrianBicyclistFilter;
import com.syberry.poc.data.dto.enums.WeekDay;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * A component that builds a Specification for querying the database
 * for PedestrianBicyclist entities based on various filtering criteria
 * provided in a PedestrianBicyclistFilter object.
 */
@Component
@RequiredArgsConstructor
public class PedestrianBicyclistSpecification {

  /**
   * Builds a Specification for finding all Users that match the given filter.
   *
   * @param filter the filter to apply when searching for users
   * @return a Specification for finding all matching users
   */
  public Specification<PedestrianBicyclist> buildPedestrianBicyclistSpecification(
      PedestrianBicyclistFilter filter) {
    return buildYearEqualSpecification(filter.getYear())
        .and(buildMonthEqualSpecification(filter.getMonth()))
        .and(buildDayEqualSpecification(filter.getDay()))
        .and(buildWeekendEqualSpecification(
            EnumConverter.convertToEntity(filter.getWeekDay(), WeekDay.class)))
        .and(buildDocumentIdEqualSpecification(filter.getDocumentId()));
  }

  /**
   * Builds a Specification for matching PedestrianBicyclist with a given year.
   *
   * @param year the year to match against
   * @return a Specification for matching PedestrianBicyclist with a given year
   */
  private Specification<PedestrianBicyclist> buildYearEqualSpecification(Integer year) {
    return (root, query, criteriaBuilder) ->
        year != null
            ? criteriaBuilder.equal(root.get(PedestrianBicyclist_.YEAR), year)
            : null;
  }

  /**
   * Builds a Specification for matching PedestrianBicyclist with a given month.
   *
   * @param month the month to match against
   * @return a Specification for matching PedestrianBicyclist with a given month
   */
  private Specification<PedestrianBicyclist> buildMonthEqualSpecification(Integer month) {
    return (root, query, criteriaBuilder) ->
        month != null
            ? criteriaBuilder.equal(root.get(PedestrianBicyclist_.MONTH), month)
            : null;
  }

  /**
   * Builds a Specification for matching PedestrianBicyclist with a given day.
   *
   * @param day the day to match against
   * @return a Specification for matching PedestrianBicyclist with a given day
   */
  private Specification<PedestrianBicyclist> buildDayEqualSpecification(Integer day) {
    return (root, query, criteriaBuilder) ->
        day != null
            ? criteriaBuilder.equal(root.get(PedestrianBicyclist_.DAY), day)
            : null;
  }

  /**
   * Builds a Specification for matching PedestrianBicyclist with a given weekDay.
   *
   * @param weekDay the weekDay to match against
   * @return a Specification for matching PedestrianBicyclist with a given weekDay
   */
  private Specification<PedestrianBicyclist> buildWeekendEqualSpecification(WeekDay weekDay) {
    return (root, query, criteriaBuilder) ->
        weekDay != null
            ? criteriaBuilder.equal(root.get(PedestrianBicyclist_.WEEK_DAY), weekDay)
            : null;
  }

  /**
   * Builds a Specification for matching PedestrianBicyclist with given documentId.
   *
   * @param documentId the documentId to match against
   * @return a Specification for matching PedestrianBicyclist with given documentId
   */
  private Specification<PedestrianBicyclist> buildDocumentIdEqualSpecification(Long documentId) {
    return (root, query, criteriaBuilder) -> documentId != null
        ? criteriaBuilder.equal(
            root.get(PedestrianBicyclist_.DOCUMENT).get(Document_.ID), documentId)
        : null;
  }
}
