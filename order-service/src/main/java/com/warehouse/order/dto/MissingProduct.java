package com.warehouse.order.dto;

public class MissingProduct {

  private String productId;
  private double missingKg;

  public MissingProduct(String productId, double missingKg) {
    this.productId = productId;
    this.missingKg = missingKg;
  }

  public String getProductId() {
    return productId;
  }

  public double getMissingKg() {
    return missingKg;
  }
}
