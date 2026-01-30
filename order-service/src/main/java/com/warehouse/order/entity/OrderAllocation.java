package com.warehouse.order.entity;

public class OrderAllocation {

  private String inventoryItemId;
  private String productCode;
  private double allocatedKg;

  public OrderAllocation() {
  }

  public OrderAllocation(String inventoryItemId, String productCode, double allocatedKg) {
    this.inventoryItemId = inventoryItemId;
    this.productCode = productCode;
    this.allocatedKg = allocatedKg;
  }

  public String getInventoryItemId() {
    return inventoryItemId;
  }

  public void setInventoryItemId(String inventoryItemId) {
    this.inventoryItemId = inventoryItemId;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public double getAllocatedKg() {
    return allocatedKg;
  }

  public void setAllocatedKg(double allocatedKg) {
    this.allocatedKg = allocatedKg;
  }
}
