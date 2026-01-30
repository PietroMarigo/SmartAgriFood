package com.warehouse.order.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.warehouse.order.entity.InventoryItem;

public interface InventoryRepository
    extends MongoRepository<InventoryItem, String> {

  List<InventoryItem> findByProductCodeOrderByExpirationDateAsc(String productCode);
}
