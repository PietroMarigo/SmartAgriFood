package com.warehouse.auth.integration;

import com.warehouse.auth.AuthServiceApplication;
import com.warehouse.auth.config.SecurityConfig;
import com.warehouse.auth.dto.*;
import com.warehouse.auth.exception.*;
import com.warehouse.auth.repository.UserRepository;
import com.warehouse.auth.service.AuthService;
import com.warehouse.auth.entity.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = { AuthServiceApplication.class, SecurityConfig.class })
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest {

  @Autowired
  AuthService authService;
  UserRepository userRepository;

  private UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

  @Test
  @Order(1)
  public void testAlreadyUsedEmail() {
    System.out.println("Test email già usata");
    UserRegisterRequest registerRequest = userRegisterRequest.newUserRegisterRequest("pippo", "baudo",
        "mario.rossi@warehouse.it", "pippo123", UserRole.MAGAZZINIERE);
    System.out.println(registerRequest.toString());

    assertThrows(UserAlreadyExistsException.class, () -> {
      authService.register(registerRequest);
    }, "In teoria dovrebbe essere un'eccezione");
  }

  @Test
  @Order(2)
  public void testInvalidCredentials() {
    System.out.println("Test che tu non faccia il biricchino");
    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.newUserRegisterRequest("", "", "", "", UserRole.UFFICIO);
    System.out.println(registerRequest.toString());

    assertThrows(InvalidCredentialsException.class, () -> {
      authService.register(registerRequest);
    }, "Dovrebbe esserci una eccezione");
  }

  @Test
  @Order(3)
  public void testSuccessfulRegistration() {
    System.out.println("Three times a charm");
    UserRegisterRequest registerRequest = userRegisterRequest.newUserRegisterRequest("Paolo", "Rossi",
        "paolo.rossi@example.com", "pippo123", UserRole.UFFICIO);
    System.out.println(registerRequest.toString());
    UserRegisterResponse response = authService.register(registerRequest);
    System.out.println(response.getUsername());
    String username = response.getUsername();

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername(username);
    loginRequest.setPassword("pippo123");
    assertNotNull(authService.login(loginRequest));
  }
}
