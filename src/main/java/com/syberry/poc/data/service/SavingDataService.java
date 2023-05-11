package com.syberry.poc.data.service;

import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.database.entity.PedestrianBicyclistValues;
import com.syberry.poc.data.database.entity.Traffic;
import java.util.List;

/**
 * Service interface for saving processed document data.
 */
public interface SavingDataService {
  void savePedestrianBicyclist(List<PedestrianBicyclist> entities);

  void savePedestrianBicyclistValues(List<PedestrianBicyclistValues> entities);

  void saveCrashData(List<CrashData> entities);

  void saveTraffic(List<Traffic> entities);
}

