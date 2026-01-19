package com.warehouse.ware.repository;

import com.warehouse.ware.entity.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {
  // Basic CRUD operations like .save() are included automatically
}
