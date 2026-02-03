package com.warehouse.order.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.common.dto.OrderRequest;
import com.warehouse.common.dto.OrderResponse;
import com.warehouse.common.dto.PickingTask;
import com.warehouse.common.entity.Batch;
import com.warehouse.common.entity.Order;
import com.warehouse.common.entity.OrderItem;
import com.warehouse.common.entity.OrderStatus;
import com.warehouse.common.repository.BatchRepository;
import com.warehouse.common.repository.OrderRepository;

@Service
public class OrderService {

  private final BatchRepository batchRepository;
  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(BatchRepository batchRepository, OrderRepository orderRepository) {
    this.batchRepository = batchRepository;
    this.orderRepository = orderRepository;
  }

  public OrderResponse processOrder(OrderRequest request) {

    Order order = new Order(request);
    List<PickingTask> pickingList = new ArrayList<>();

    for (OrderItem item : request.getItems()) {
      double remainingKg = item.getWeightKg();

      List<Batch> batches = batchRepository
          .findByProductSkuAndStatusOrderByExpiryDateAsc(
              item.getProductSku(),
              "AVAILABLE");

      Map<String, Double> batchAllocation = new HashMap<>();

      for (Batch batch : batches) {

        if (remainingKg <= 0)
          break;

        double availableKg = batch.getWeightKg();
        double takenKg = Math.min(remainingKg, availableKg);

        if (takenKg > 0) {
          batchAllocation.put(batch.getBatchId(), takenKg);
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
      order.getItems().put(item.getProductSku(), batchAllocation);
    }
    order.setStatus(OrderStatus.NOT_PROCESSED);
    Order savedOrder = orderRepository.save(order);

    return new OrderResponse(savedOrder.getId(), pickingList);
  }
}
