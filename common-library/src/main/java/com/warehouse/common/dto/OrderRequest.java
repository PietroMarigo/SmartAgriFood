package com.warehouse.common.dto;

import java.time.LocalDate;
import java.util.List;

import com.warehouse.common.entity.OrderItem;

public class OrderRequest {
  private String customer;
  private LocalDate pickupDate;
  private String loadingZone;
  private List<OrderItem> items;

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public LocalDate getPickupDate() {
    return pickupDate;
  }

  public void setPickupDate(LocalDate pickupDate) {
    this.pickupDate = pickupDate;
  }

  public String getLoadingZone() {
    return loadingZone;
  }

  public void setLoadingZone(String loadingZone) {
    this.loadingZone = loadingZone;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
  }

  public OrderRequest(List<OrderItem> list, String customer, LocalDate pickupDate) {
    this.setItems(list);
    this.setCustomer(customer);
    this.setPickupDate(pickupDate);
  }
}
