package com.warehouse.common.entity;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.warehouse.common.dto.OrderRequest;

@Document(collection = "orders")
public class Order {

  @Id
  private String id;
  private LocalDateTime createdAt;
  private String customer;
  private LocalDate pickupDate;
  private OrderStatus status;
  private String loadingZone;
  private String workerUsername;
  private Map<String, Map<String, Double>> items = new HashMap<>();
  private List<OrderItem> list;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

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

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getLoadingZone() {
    return loadingZone;
  }

  public void setLoadingZone(String loadingZone) {
    this.loadingZone = loadingZone;
  }

  public Map<String, Map<String, Double>> getItems() {
    return items;
  }

  public void setItems(Map<String, Map<String, Double>> items) {
    this.items = items;
  }

  public List<OrderItem> getList() {
    return list;
  }

  public void setList(List<OrderItem> list) {
    this.list = list;
  }

  public String getWorkerUsername() {
    return workerUsername;
  }

  public void setWorkerUsername(String workerUsername) {
    this.workerUsername = workerUsername;
  }

  public Order() {

  }

  public Order(OrderRequest request) {
    this.setCustomer(request.getCustomer());
    this.setPickupDate(request.getPickupDate());
    this.setLoadingZone(request.getLoadingZone());
    this.setCreatedAt(LocalDateTime.now());
  }
}
