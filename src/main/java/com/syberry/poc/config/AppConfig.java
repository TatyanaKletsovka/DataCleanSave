package com.syberry.poc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.syberry.poc.data.dto.enums.DocumentType;
import com.syberry.poc.data.service.DocumentTypeConverter;
import com.syberry.poc.data.service.impl.CrashDataConverter;
import com.syberry.poc.data.service.impl.PedestrianBicyclistConverter;
import com.syberry.poc.data.service.impl.TrafficConverter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration bean for setting up application.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final PedestrianBicyclistConverter pedestrianBicyclistConverter;
  private final TrafficConverter trafficConverter;
  private final CrashDataConverter crashDataConverter;

  /**
   * Defines object mapper bean.
   *
   * @return object mapper configured with support for Java Date and Time API
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  /**
   * Defines bean with existing converters.
   *
   * @return map with DocumentTypes and DocumentTypeConverters
   */
  @Bean
  public Map<DocumentType, DocumentTypeConverter> documentMapConverter() {
    Map<DocumentType, DocumentTypeConverter> map = new HashMap<>();
    map.put(DocumentType.CRASH, crashDataConverter);
    map.put(DocumentType.PEDESTRIAN, pedestrianBicyclistConverter);
    map.put(DocumentType.TRAFFIC, trafficConverter);
    return map;
  }
}
