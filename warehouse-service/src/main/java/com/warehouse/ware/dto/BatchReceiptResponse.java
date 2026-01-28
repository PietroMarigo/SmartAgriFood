package com.warehouse.ware.dto;

import java.time.LocalDate;

import com.warehouse.ware.entity.Batch;

public class BatchReceiptResponse {
  private String batchId;
  private String productSku;
  private String locationCode;
  private LocalDate expiryDate;
  private String status;

  public BatchReceiptResponse(String batchId, String productSku, String locationCode, LocalDate expiryDate,
      String status) {
    this.batchId = batchId;
    this.productSku = productSku;
    this.locationCode = locationCode;
    this.expiryDate = expiryDate;
    this.status = status;
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

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BatchReceiptResponse() {
  }

  public BatchReceiptResponse receiptResponse(Batch batch) {
    BatchReceiptResponse br = new BatchReceiptResponse();
    br.setBatchId(batch.getBatchId());
    br.setProductSku(batch.getProductSku());
    br.setLocationCode(batch.getLocationCode());
    br.setExpiryDate(batch.getExpiryDate());
    br.setStatus(batch.getStatus());
    return br;
  }
}
