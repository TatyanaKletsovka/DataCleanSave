package com.syberry.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Data POC application.
 * It uses the Spring Boot framework to configure and run the application.
 */
@Slf4j
@SpringBootApplication
public class DataPocApplication {
  public static void main(String[] args) {
    log.info("The application is running");
    SpringApplication.run(DataPocApplication.class, args);
  }
}
