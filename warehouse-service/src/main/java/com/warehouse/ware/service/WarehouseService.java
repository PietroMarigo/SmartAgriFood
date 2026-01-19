package com.warehouse.ware.service;

import com.warehouse.ware.entity.Batch;
import com.warehouse.ware.entity.Products;
import com.warehouse.ware.repository.BatchRepository;
import com.warehouse.ware.repository.ProductRepository;
import com.warehouse.ware.exception.*;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
  private final BatchRepository batchRepository;
  private final ProductRepository productRepository;

  @Autowired
  public WarehouseService(BatchRepository batchRepository, ProductRepository productRepository) {
    this.batchRepository = batchRepository;
    this.productRepository = productRepository;
  }

  private boolean isBlank(String value) {
    return value == null || value.isEmpty();
  }

  public Products returnProduct(String sku) {
    boolean skuProvided = !isBlank(sku);
    if (!skuProvided)
      throw new ProductNotFoundException("Codice sku non è stato fornito");
    if (!productRepository.existsBySku(sku))
      throw new ProductNotFoundException("Non esiste un prodotto associato a questo codice");
    return productRepository.findBySku(sku).orElse(null);
  }

  public Batch receiveGoods(String sku, Double weight, String locationCode) {
    Products product = productRepository.findBySku(sku)
        .orElseThrow(() -> new ProductNotFoundException("Il condice SKU non ha restituito nessun prodotto" + sku));
    LocalDate expirationDate = LocalDate.now().plusDays(product.getShelfLifeDays());

    Batch newBatch = new Batch();
    newBatch = newBatch.batchBuilder(sku, weight, locationCode, expirationDate, "Disponibile");
    return batchRepository.save(newBatch);
  }
}
