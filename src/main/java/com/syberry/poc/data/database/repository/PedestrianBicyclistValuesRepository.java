package com.syberry.poc.data.database.repository;

import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing pedestrian and bicyclist values in the database.
 */
@Repository
public interface PedestrianBicyclistValuesRepository
    extends JpaRepository<PedestrianBicyclistValues, Long> {

  /**
   * Deletes pedestrianBicyclistValues entity containing provided pedestrianBicyclist entity.
   *
   * @param pedestrianBicyclist entity
   */
  void deleteByPedestrianBicyclist(PedestrianBicyclist pedestrianBicyclist);
}
