package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.database.entity.PedestrianBicyclist;
import com.syberry.poc.data.service.DocumentTypeConverter;
import com.syberry.poc.data.service.SavingDataService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for saving document's data for PedestrianBicyclist document type.
 */
@Service
@RequiredArgsConstructor
public class PedestrianBicyclistConverter implements DocumentTypeConverter {
  private final SavingDataService savingDataService;
  private final DataConverter dataConverter;

  @Override
  public int save(List<Map<String, String>> proceededDocument, Document document) {
    List<PedestrianBicyclist> entities = dataConverter.convertToPedestrianBicyclistList(
        proceededDocument, document);
    savingDataService.savePedestrianBicyclist(
        dataConverter.convertToPedestrianBicyclistList(proceededDocument, document)
    );
    savingDataService.savePedestrianBicyclistValues(
        dataConverter.convertToPedestrianBicyclistValuesList(proceededDocument)
    );
    return entities.size();
  }
}
