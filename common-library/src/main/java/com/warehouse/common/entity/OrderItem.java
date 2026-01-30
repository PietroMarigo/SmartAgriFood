package com.warehouse.common.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class OrderItem {

  @Field("productSku")
  private String productSku;

  @Field("weightKg")
  private double weightKg;

  public OrderItem() {
    // costruttore vuoto richiesto da Spring / Mongo
  }

  public OrderItem(String productSku, double weightKg) {
    this.productSku = productSku;
    this.weightKg = weightKg;
  }

  // ======================
  // GETTER
  // ======================

  public String getProductSku() {
    return productSku;
  }

  public double getWeightKg() {
    return weightKg;
  }

  // ======================
  // SETTER
  // ======================

  public void setProductSku(String productSku) {
    this.productSku = productSku;
  }

  public void setWeightKg(double weightKg) {
    this.weightKg = weightKg;
  }

  // ======================
  // DEBUG
  // ======================

  @Override
  public String toString() {
    return "OrderItem{" +
        "productSku='" + productSku + '\'' +
        ", weightKg=" + weightKg +
        '}';
  }
}
