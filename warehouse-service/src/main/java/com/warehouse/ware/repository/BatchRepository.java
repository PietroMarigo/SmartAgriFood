package com.warehouse.ware.repository;

import com.warehouse.ware.entity.Batch;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {
  Optional<Batch> findByBatchId(String batchId);
}
