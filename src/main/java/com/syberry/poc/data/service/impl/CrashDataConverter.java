package com.syberry.poc.data.service.impl;

import com.syberry.poc.data.converter.DataConverter;
import com.syberry.poc.data.database.entity.CrashData;
import com.syberry.poc.data.database.entity.Document;
import com.syberry.poc.data.service.DocumentTypeConverter;
import com.syberry.poc.data.service.SavingDataService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for saving document's data for CrashData document type.
 */
@Service
@RequiredArgsConstructor
public class CrashDataConverter implements DocumentTypeConverter {
  private final SavingDataService savingDataService;
  private final DataConverter dataConverter;

  @Override
  public int save(List<Map<String, String>> proceededDocument, Document document) {
    List<CrashData> entities = dataConverter.convertToCrashDataList(proceededDocument, document);
    savingDataService.saveCrashData(entities);
    return entities.size();

  }
}
