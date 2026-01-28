package com.warehouse.ware.service;

import com.warehouse.ware.entity.Batch;
import com.warehouse.ware.entity.Movment;
import com.warehouse.ware.entity.Products;
import com.warehouse.ware.repository.BatchRepository;
import com.warehouse.ware.repository.MovementRepository;
import com.warehouse.ware.repository.ProductRepository;
import com.warehouse.ware.exception.*;
import com.warehouse.ware.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
  private final BatchRepository batchRepository;
  private final ProductRepository productRepository;
  private final MovementRepository movementRepository;

  @Autowired
  public WarehouseService(BatchRepository batchRepository, ProductRepository productRepository,
      MovementRepository movementRepository) {
    this.batchRepository = batchRepository;
    this.productRepository = productRepository;
    this.movementRepository = movementRepository;
  }

  private boolean isBlank(String value) {
    return value == null || value.isEmpty();
  }

  public Products returnProduct(String batchId) {
    boolean idProvided = !isBlank(batchId);
    if (!idProvided)
      throw new ProductNotFoundException("Codice sku non è stato fornito");
    if (!productRepository.existsBySku(Batch.extractSku(batchId)))
      throw new ProductNotFoundException("Non esiste un prodotto associato a questo codice");
    return productRepository.findBySku(Batch.extractSku(batchId)).orElse(null);
  }

  public BatchReceiptResponse receiveGoods(BatchReceiptRequest batchRequest) {
    String sku = Batch.extractSku(batchRequest.getBatchId());
    System.out.println("Sku" + sku);
    Products product = productRepository.findBySku(sku)
        .orElseThrow(() -> new ProductNotFoundException("Il condice SKU non ha restituito nessun prodotto" + sku));
    LocalDate expirationDate = LocalDate.now().plusDays(product.getShelfLifeDays());

    Batch newBatch = new Batch(batchRequest.getBatchId(), batchRequest.getWeight(), batchRequest.getLocationCode(),
        expirationDate, "Disponibile", LocalDateTime.now());
    Batch savedBatch = batchRepository.save(newBatch);
    return new BatchReceiptResponse().receiptResponse(savedBatch);
  }

  public MoveResponse moveBatch(String batchId, String newZone, String workerUsername) {
    Batch batch = batchRepository.findByBatchId(batchId).orElse(null);
    if (batch == null)
      throw new RuntimeException("Batch not found");
    Movment move = new Movment(batchId, batch.getLocationCode(), newZone, workerUsername, LocalDateTime.now());
    batch.setLocationCode(newZone);
    batchRepository.save(batch);
    movementRepository.save(move);
    return new MoveResponse(move);
  }
}
