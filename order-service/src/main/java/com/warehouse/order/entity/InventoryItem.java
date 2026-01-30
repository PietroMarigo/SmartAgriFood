package com.warehouse.order.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class InventoryItem {

  @Id
  private String id;

  private String productCode;
  private double availableWeightKg;
  private LocalDate expirationDate;

  public InventoryItem(String apple, int par) {
  }

  public InventoryItem(String productCode, double availableWeightKg, LocalDate expirationDate) {
    this.productCode = productCode;
    this.availableWeightKg = availableWeightKg;
    this.expirationDate = expirationDate;
  }

  public String getId() {
    return id;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public double getAvailableWeightKg() {
    return availableWeightKg;
  }

  public void setAvailableWeightKg(double availableWeightKg) {
    this.availableWeightKg = availableWeightKg;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }
}
