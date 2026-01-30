package com.warehouse.order.dto;

import java.util.List;

import com.warehouse.order.enumtype.OrderFulfillmentStatus;

public class OrderFulfillmentResult {

  private String orderId;
  private OrderFulfillmentStatus status;
  private List<MissingProduct> missingProducts;

  public OrderFulfillmentResult(String orderId,
      OrderFulfillmentStatus status,
      List<MissingProduct> missingProducts) {
    this.orderId = orderId;
    this.status = status;
    this.missingProducts = missingProducts;
  }

  public String getOrderId() {
    return orderId;
  }

  public OrderFulfillmentStatus getStatus() {
    return status;
  }

  public List<MissingProduct> getMissingProducts() {
    return missingProducts;
  }
}
