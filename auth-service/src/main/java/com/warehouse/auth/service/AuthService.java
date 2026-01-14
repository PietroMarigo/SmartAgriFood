package com.warehouse.auth.service;

import com.warehouse.auth.dto.*;
import com.warehouse.auth.entity.User;
import com.warehouse.auth.exception.InvalidCredentialsException;
import com.warehouse.auth.exception.UserAlreadyExistsException;
import com.warehouse.auth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private boolean isBlank(String value) {
    return value == null || value.isEmpty();
  }

  public LoginResponse login(LoginRequest loginRequest) {
    boolean usernameProvided = !isBlank(loginRequest.getUsername());
    boolean emailProvided = !isBlank(loginRequest.getEmail());
    if (!usernameProvided && !emailProvided) {
      throw new InvalidCredentialsException("Username or Email is required");
    }
    if (isBlank(loginRequest.getPassword())) {
      throw new InvalidCredentialsException("Password cannot be empty");
    }

    User user = null;
    if (usernameProvided) {
      user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
      // System.out.println(user.toString());
    } else {
      user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
      // System.out.println(user.toString());
    }

    if (user == null) {
      throw new InvalidCredentialsException("Username or email doesn't exists");
    }

    // System.out.println(loginRequest.getPassword() + " " + user.getPassword() + "
    // "
    // + passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
    // System.out.println(passwordEncoder.getClass().getName());
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new InvalidCredentialsException("The password is incorrect");
    }

    if (!user.getActive()) {
      throw new InvalidCredentialsException(
          "The account is inactive, if this is a surprise you might receive a call from HR soon... good luck");
    }

    return new LoginResponse(
        user.getId(),
        user.getName(),
        user.getSurname(),
        user.getUsername(),
        user.getEmail(),
        user.getRole());
  }

  public UserRegisterResponse register(UserRegisterRequest registRequest) {
    boolean nameProvided = !isBlank(registRequest.getName());
    boolean surnameProvided = !isBlank(registRequest.getSurname());
    boolean emailProvided = !isBlank(registRequest.getEmail());
    boolean passProvided = !isBlank(registRequest.getPassword());
    // boolean roleProvided = !isBlank(registRequest.getRole());

    if (!nameProvided || !surnameProvided || !emailProvided) {
      throw new InvalidCredentialsException("Non sono stati fornite tutte le generalità");
    }

    if (!passProvided) {
      throw new InvalidCredentialsException("Non è stata fornita una password");
    }

    if (registRequest.getRole() == null) {
      throw new InvalidCredentialsException("Non è stato fornito un ruolo");
    }

    var checkEmail = userRepository.findByEmail(registRequest.getEmail()).orElse(null);
    if (checkEmail != null)
      throw new UserAlreadyExistsException("L'email è già in uso, con ogni probabilità l'utente esiste già");

    int idUserName = (int) userRepository.count() + 1;
    String initials = (registRequest.getName().substring(0, 1) + registRequest.getSurname().substring(0, 1))
        .toUpperCase();
    String userName = String.format("%s%03d", initials, idUserName);

    User newUser = new User();
    newUser.newUser(
        registRequest.getName(),
        registRequest.getSurname(),
        userName,
        registRequest.getEmail(),
        registRequest.getPassword(),
        registRequest.getRole());
    User savedUser = userRepository.save(newUser);
    System.out.println(savedUser.toString());
    return new UserRegisterResponse().registerResponse(savedUser);
    // savedUser.getId(),
    // savedUser.getName(),
    // savedUser.getSurname(),
    // savedUser.getEmail(),
    // savedUser.getRole());
  }
}
