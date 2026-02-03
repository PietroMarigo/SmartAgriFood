package com.warehouse.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
@EntityScan(basePackages = {
    "com.warehouse.common.entity",
    "com.warehouse.common.repository"
})
@EnableMongoRepositories(basePackages = {
    "com.warehouse.common.repository"
})
@EnableFeignClients
public class OfficeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OfficeServiceApplication.class, args);
  }
}
