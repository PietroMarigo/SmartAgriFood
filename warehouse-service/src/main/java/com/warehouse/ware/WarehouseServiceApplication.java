package com.warehouse.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.warehouse.ware.entity", "com.warehouse.common.entity" })
@EnableJpaRepositories(basePackages = { "com.warehouse.ware.repository", "com.warehouse.common.repository" })
@EnableMongoRepositories(basePackages = { "com.warehouse.ware.repository", "com.warehouse.common.repository" })
public class WarehouseServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WarehouseServiceApplication.class, args);
  }
}
