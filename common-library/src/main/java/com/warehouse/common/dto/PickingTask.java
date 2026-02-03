package com.warehouse.common.dto;

import java.time.LocalDate;

public class PickingTask {

  private String productSku;
  private String batchId;
  private String locationCode;
  private double takenKg;
  private LocalDate expiryDate;

  public PickingTask(String productSku,
      String batchId,
      String locationCode,
      double takenKg,
      LocalDate expiryDate) {
    this.productSku = productSku;
    this.batchId = batchId;
    this.locationCode = locationCode;
    this.takenKg = takenKg;
    this.expiryDate = expiryDate;
  }

  public String getProductSku() {
    return productSku;
  }

  public String getBatchId() {
    return batchId;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public double getTakenKg() {
    return takenKg;
  }

  public LocalDate getExpiryDate() {
    return expiryDate;
  }
}
