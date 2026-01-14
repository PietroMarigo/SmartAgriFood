package com.warehouse.auth.integration;

import com.warehouse.auth.AuthServiceApplication;
import com.warehouse.auth.repository.UserRepository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AuthServiceApplication.class)
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseConnectionTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DataSource dataSource;

  @Test
  @Order(1)
  public void testSQLiteConnectionSuccessful() throws Exception {
    assertNotNull(dataSource, "DataSource should not be null");

    try (Connection connection = dataSource.getConnection()) {
      assertNotNull(connection, "Connection should not be null");
      assertTrue(connection.isValid(2), "Connection should be valid");
      System.out.println("Connection to SQLite successful");
    }
  }

  @Test
  @Order(2)
  public void testSQLiteTable() throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      var statement = connection.createStatement();
      var resultQuerry = statement.executeQuery(
          "SELECT name FROM sqlite_master WHERE type='table'");

      // while (resultQuerry.next()) {
      // System.out.println(resultQuerry.getString("name"));
      // }
      assertTrue(resultQuerry.next(), "user table should exist");
      System.out.println("Sql table exists");
    }
  }

  @Test
  @Order(3)
  public void testUserExists() {
    assertNotNull(userRepository, "User Repository shouldn't be null");

    var laura = userRepository.findByUsername("LN004");
    assertTrue(laura.isPresent(), "Luara non c'è, è andata via");
    System.out.println("Laura c'è, non è andata via");
  }
}
