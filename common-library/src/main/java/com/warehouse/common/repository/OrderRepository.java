package com.warehouse.common.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.warehouse.common.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
  List<Order> findByStatus(String status);
}
