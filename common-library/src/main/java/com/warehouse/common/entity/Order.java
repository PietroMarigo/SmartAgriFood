package com.warehouse.common.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {

  @Id
  private String id;

  private LocalDateTime createdAt;
  private List<OrderItem> items;

  public Order() {
  }

  public Order(List<OrderItem> items) {
    this.items = items;
    this.createdAt = LocalDateTime.now();
  }

  // GETTER & SETTER

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
  }

  public String getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
