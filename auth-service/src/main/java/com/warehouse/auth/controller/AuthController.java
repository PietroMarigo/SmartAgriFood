package com.warehouse.auth.controller;

import com.warehouse.auth.dto.*;
import com.warehouse.auth.service.AuthService;
import com.warehouse.auth.exception.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Base path for all authentication routes
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {

      LoginResponse login = authService.login(loginRequest);
      return ResponseEntity.ok(login);

    } catch (InvalidCredentialsException e) {

      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Non autrorizzato");
      errorDetails.put("message", e.getMessage());

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);

    } catch (Exception e) {

      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Non autrorizzato");
      errorDetails.put("message", "C'è stato un errore innaspettato");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerRequest) {
    try {
      UserRegisterResponse regist = authService.register(registerRequest);
      return ResponseEntity.ok(regist);
    } catch (InvalidCredentialsException e) {

      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Registrazione fallita");
      errorDetails.put("message", e.getMessage());

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    } catch (UserAlreadyExistsException e) {

      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Utente Esistente");
      errorDetails.put("message", e.getMessage());

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    } catch (Exception e) {

      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Errore imprevisto");
      errorDetails.put("message", "C'è stato un errore innaspettato");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
  }
}
