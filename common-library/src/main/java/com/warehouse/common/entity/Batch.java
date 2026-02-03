package com.warehouse.common.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "batches")
public class Batch {
  @Id
  private String batchId;
  private String productSku;
  private String productName;
  private Double weightKg;
  private LocalDate expiryDate;
  private String locationCode;
  private String status = "AVAILABLE";
  private String provider;
  private String harvestDate;
  private String increment;
  private LocalDateTime timeStamp;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getHarvestDate() {
    return harvestDate;
  }

  public void setHarvestDate(String harvestDate) {
    this.harvestDate = harvestDate;
  }

  public String getIncrement() {
    return increment;
  }

  public void setIncrement(String increment) {
    this.increment = increment;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getProductSku() {
    return productSku;
  }

  public void setProductSku(String productSku) {
    this.productSku = productSku;
  }

  public Double getWeightKg() {
    return weightKg;
  }

  public void setWeightKg(Double weightKg) {
    this.weightKg = weightKg;
  }

  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Batch() {
  }

  public Batch(String batchId, String productName, double weight, String locationCode, LocalDate expiryDate,
      String status,
      LocalDateTime timeStamp) {
    this.setBatchId(batchId);
    String[] info = extractInfo(batchId);
    if (info.length < 4) {
      throw new IllegalArgumentException(
          "Il Batch ID deve seguire il formato: SKU-Fornitore-Data-Incremento (es. A001-003-20250105-001)");
    }
    this.setProductSku(info[0]);
    this.setProductName(productName);
    this.setWeightKg(weight);
    this.setLocationCode(locationCode);
    this.setExpiryDate(expiryDate);
    this.setProvider(info[1]);
    this.setHarvestDate(info[2]);
    this.setIncrement(info[3]);
    this.setStatus(status);
    this.setTimeStamp(timeStamp);
  }

  public static String[] extractInfo(String batchId) {
    return batchId.split("-");
  }

  public static String extractSku(String batchId) {
    String[] r = extractInfo(batchId);
    return r[0];
  }
}
