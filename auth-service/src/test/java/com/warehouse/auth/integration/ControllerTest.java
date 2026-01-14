package com.warehouse.auth.integration;

import com.warehouse.auth.dto.*;
// import com.warehouse.auth.exception.InvalidCredentialsException;
import com.warehouse.auth.entity.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.jayway.jsonpath.InvalidCriteriaException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Order(1)
  @Test
  public void testLoginInvalidCredentials() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("");
    loginRequest.setPassword("");

    try {
      mockMvc.perform(post("/api/auth/login")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest)))
          .andDo(print())
          .andExpect(status().isUnauthorized());
      // .andExpect(jsonPath("$.message").value("Username or Email is reuired"));
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Message: " + e.getCause().getMessage());
      }
    }
  }

  @Order(2)
  @Test
  public void testLoginEmptyPassword() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("MR001");
    loginRequest.setPassword("");

    try {
      mockMvc.perform(post("/api/auth/login")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest)))
          .andDo(print())
          .andExpect(status().isUnauthorized());
      // .andExpect(jsonPath("$.message").value("Username or Email is reuired"));
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Message: " + e.getCause().getMessage());
      }
    }
  }

  @Order(3)
  @Test
  public void testLoginWrongPassword() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("MR001");
    loginRequest.setPassword("nuhu");

    try {
      mockMvc.perform(post("/api/auth/login")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest)))
          .andDo(print())
          .andExpect(status().isUnauthorized());
      // .andExpect(jsonPath("$.message").value("Username or Email is reuired"));
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Message: " + e.getCause().getMessage());
      }
    }
  }

  @Order(4)
  @Test
  public void testLoginSuccessful() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("MR001");
    loginRequest.setPassword("pippo123");

    try {
      mockMvc.perform(post("/api/auth/login")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginRequest)))
          .andDo(print())
          .andExpect(status().isOk());
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Message: " + e.getCause().getMessage());
      }
    }
  }

  @Order(5)
  @Test
  public void testRegisterInvalid() {
    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setName("");
    registerRequest.setSurname("");
    registerRequest.setEmail("");
    registerRequest.setPassword("");
    registerRequest.setRole(UserRole.MAGAZZINIERE);
    try {
      mockMvc.perform(post("/api/auth/register")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(registerRequest)))
          .andDo(print())
          .andExpect(status().isUnauthorized());
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Message: " + e.getCause().getMessage());
      }
    }
  }

  @Order(6)
  @Test
  public void testRegisterAlreadyInUse() {
    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setName("Ciro");
    registerRequest.setSurname("Pasquale");
    registerRequest.setEmail("mario@example.com");
    registerRequest.setPassword("pippo123");
    registerRequest.setRole(UserRole.MAGAZZINIERE);

    try {
      mockMvc.perform(post("/api/auth/register")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(registerRequest)))
          .andDo(print())
          .andExpect(status().isUnauthorized());
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Messege: " + e.getCause().getMessage());
      }
    }
  }

  @Order(7)
  @Test
  public void testRegisterSuccess() {
    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setName("Ciro");
    registerRequest.setSurname("Pasquale");
    registerRequest.setEmail("ciro@example.com");
    registerRequest.setPassword("pippo123");
    registerRequest.setRole(UserRole.MAGAZZINIERE);

    try {
      mockMvc.perform(post("/api/auth/register")
          .with(csrf())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(registerRequest)))
          .andDo(print())
          .andExpect(status().isOk());
    } catch (Exception e) {
      System.out.println("Caught exception: " + e.getClass().getName());
      if (e.getCause() != null) {
        System.out.println("Root cause: " + e.getCause().getClass().getName());
        System.out.println("Messege: " + e.getCause().getMessage());
      }
    }
  }
}
