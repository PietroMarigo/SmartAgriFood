package com.warehouse.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.warehouse.ware.repository")
@EnableMongoRepositories(basePackages = "com.warehouse.ware.repository")
public class WarehouseServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WarehouseServiceApplication.class, args);
  }
}
