package com.warehouse.common.repository;

import com.warehouse.common.entity.Batch;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {
  Optional<Batch> findByBatchId(String batchId);

  List<Batch> findByProductSkuAndStatusOrderByExpiryDateAsc(
      String productSku,
      String status);

  List<Batch> findByStatus(String status);
}
