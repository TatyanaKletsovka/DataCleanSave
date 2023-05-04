package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.database.entity.Traffic;
import com.syberry.poc.data.database.repository.CrashDataRepository;
import com.syberry.poc.data.database.repository.PedestrianBicyclistRepository;
import com.syberry.poc.data.database.repository.PedestrianBicyclistValuesRepository;
import com.syberry.poc.data.database.repository.TrafficRepository;
import com.syberry.poc.data.service.SavingDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for processed data entities saving.
 */
@Service
@RequiredArgsConstructor
public class SavingDataServiceImpl implements SavingDataService {
  private final PedestrianBicyclistRepository pedestrianBicyclist;
  private final PedestrianBicyclistValuesRepository pedestrianBicyclistValues;
  private final CrashDataRepository crashData;
  private final TrafficRepository traffic;

  /**
   * Saves list of PedestrianBicyclist entities.
   *
   * @param entities a list of PedestrianBicyclist entities.
   */
  @Override
  public void savePedestrianBicyclist(List<PedestrianBicyclist> entities) {
    pedestrianBicyclist.saveAll(entities);
  }

  /**
   * Saves list of PedestrianBicyclistValues entities.
   *
   * @param entities a list of PedestrianBicyclistValues entities.
   */
  @Override
  public void savePedestrianBicyclistValues(List<PedestrianBicyclistValues> entities) {
    pedestrianBicyclistValues.saveAll(entities);
  }

  /**
   * Saves list of CrashData entities.
   *
   * @param entities a list of CrashData entities.
   */
  @Override
  public void saveCrashData(List<CrashData> entities) {
    crashData.saveAll(entities);
  }

  /**
   * Saves list of Traffic entities.
   *
   * @param entities a list of Traffic entities.
   */
  @Override
  public void saveTraffic(List<Traffic> entities) {
    traffic.saveAll(entities);
  }
}
