package com.warehouse.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.warehouse.order.dto.PickingTask;
import com.warehouse.common.entity.Batch;
import com.warehouse.common.entity.Order;
import com.warehouse.common.entity.OrderItem;
import com.warehouse.common.repository.BatchRepository;

@Service
public class OrderService {

  private final BatchRepository batchRepository;

  public OrderService(BatchRepository batchRepository) {
    this.batchRepository = batchRepository;
  }

  /**
   * FEFO picking logic
   */
  public List<PickingTask> processOrder(Order order) {

    List<PickingTask> pickingList = new ArrayList<>();

    for (OrderItem item : order.getItems()) {

      double remainingKg = item.getWeightKg();

      List<Batch> batches = batchRepository
          .findByProductSkuAndStatusOrderByExpiryDateAsc(
              item.getProductSku(),
              "AVAILABLE");

      for (Batch batch : batches) {

        if (remainingKg <= 0)
          break;

        double availableKg = batch.getWeightKg();
        double takenKg = Math.min(remainingKg, availableKg);

        if (takenKg > 0) {
          pickingList.add(
              new PickingTask(
                  batch.getProductSku(),
                  batch.getBatchId(),
                  batch.getLocationCode(),
                  takenKg,
                  batch.getExpiryDate()));

          batch.setWeightKg(availableKg - takenKg);

          if (batch.getWeightKg() == 0) {
            batch.setStatus("EMPTY");
          }

          batchRepository.save(batch);
          remainingKg -= takenKg;
        }
      }

      if (remainingKg > 0) {
        throw new RuntimeException(
            "Disponibilità insufficiente per prodotto: " +
                item.getProductSku());
      }
    }

    return pickingList;
  }
}
