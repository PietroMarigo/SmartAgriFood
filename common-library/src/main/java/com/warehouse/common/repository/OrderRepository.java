package com.warehouse.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.warehouse.common.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
}
