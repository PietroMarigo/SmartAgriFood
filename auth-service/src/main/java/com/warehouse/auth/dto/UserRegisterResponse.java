package com.warehouse.auth.dto;

import com.warehouse.auth.entity.*;

public class UserRegisterResponse {
  private Integer userId;
  private String name;
  private String surname;
  private String username;
  private String email;
  private UserRole role;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public UserRegisterResponse registerResponse(User user) {
    UserRegisterResponse ur = new UserRegisterResponse();
    ur.setUserId(user.getId());
    ur.setName(user.getName());
    ur.setSurname(user.getSurname());
    ur.setUsername(user.getUsername());
    ur.setEmail(user.getEmail());
    ur.setRole(user.getRole());
    return ur;
  }
}
