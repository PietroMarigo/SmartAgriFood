package com.warehouse.auth.integration;

import com.warehouse.auth.AuthServiceApplication;
import com.warehouse.auth.config.SecurityConfig;
import com.warehouse.auth.dto.*;
import com.warehouse.auth.exception.InvalidCredentialsException;
// import com.warehouse.auth.repository.UserRepository;
import com.warehouse.auth.service.AuthService;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// import javax.sql.DataSource;
// import java.sql.Connection;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = { AuthServiceApplication.class, SecurityConfig.class })
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

  // @Autowired
  // private UserRepository userRepository;

  @Autowired
  private AuthService authService;

  @Test
  @Order(1)
  public void testIncorrectLogin() {
    System.out.println("Password sbagliata");
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("MR001");
    loginRequest.setPassword("prova");

    assertThrows(
        InvalidCredentialsException.class,
        () -> {
          authService.login(loginRequest);
        },
        "In teoria ci dovrebbe essere un'eccezione");
  }

  @Test
  @Order(2)
  public void testCorrectctLogin() {
    System.out.println("Username e password corrette");
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("MR001");
    loginRequest.setPassword("pippo123");
    assertNotNull(
        authService.login(loginRequest));
  }
}
