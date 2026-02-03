package com.warehouse.common.dto;

import java.util.List;

public class OrderResponse {

  private String orderId;
  private List<PickingTask> list;

  public OrderResponse(String orderId, List<PickingTask> list) {
    this.setList(list);
    this.setOrderId(orderId);
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public List<PickingTask> getList() {
    return list;
  }

  public void setList(List<PickingTask> list) {
    this.list = list;
  }

}
