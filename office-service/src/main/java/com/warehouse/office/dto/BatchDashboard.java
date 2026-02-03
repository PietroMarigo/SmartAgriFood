package com.warehouse.office.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.warehouse.common.entity.Batch;

public class BatchDashboard {
  private String batchId;
  private String productName;
  private Double weightKg;
  private LocalDate expiryDate;
  private String locationCode;
  private String status;
  private long daysToExpire;
  private boolean isCritical;

  public BatchDashboard(Batch batch) {
    this.setBatchId(batch.getBatchId());
    this.setProductName(batch.getProductName());
    this.setWeightKg(batch.getWeightKg());
    this.setExpiryDate(batch.getExpiryDate());
    this.setLocationCode(batch.getLocationCode());
    this.setStatus(batch.getStatus());
    this.setDaysToExpire(batch.getExpiryDate());
    this.setIsCritical(this.daysToExpire);
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
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

  public long getDaysToExpire() {
    return daysToExpire;
  }

  public void setDaysToExpire(LocalDate expiryDate) {
    this.daysToExpire = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
  }

  public boolean getIsCritical() {
    return isCritical;
  }

  public void setIsCritical(long daysToExpire) {
    this.isCritical = daysToExpire < 7;
  }
}
