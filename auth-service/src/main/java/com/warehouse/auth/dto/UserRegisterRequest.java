package com.warehouse.auth.dto;

import com.warehouse.auth.entity.UserRole;

public class UserRegisterRequest {
  private String name;
  private String surname;
  private String email;
  private String password;
  private UserRole role;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "Request{" +
        "name=" + getName() +
        ", surname=" + getSurname() + '\'' +
        ", email=" + getEmail() + '\'' +
        ", password=" + getPassword() + '\'' +
        ", role=" + getRole() + '\'' +
        '}';
  }

  public UserRegisterRequest newUserRegisterRequest(String name, String surname, String email, String password,
      UserRole role) {
    UserRegisterRequest ur = new UserRegisterRequest();
    ur.setName(name);
    ur.setSurname(surname);
    ur.setEmail(email);
    ur.setPassword(password);
    ur.setRole(role);
    return ur;
  }

}
