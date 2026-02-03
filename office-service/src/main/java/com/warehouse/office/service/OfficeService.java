package com.warehouse.office.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import feign.FeignException;

import com.warehouse.common.dto.OrderRequest;
import com.warehouse.common.dto.OrderResponse;
import com.warehouse.common.entity.Batch;
import com.warehouse.common.entity.OrderItem;
import com.warehouse.common.repository.BatchRepository;
import com.warehouse.office.client.OrderClient;
import com.warehouse.office.dto.BatchDashboard;

@Service
public class OfficeService {

  private final OrderClient orderClient;
  private final BatchRepository batchRepository;

  public OfficeService(OrderClient orderClient, BatchRepository batchRepository) {
    this.orderClient = orderClient;
    this.batchRepository = batchRepository;
  }

  public OrderResponse createOrderList(List<OrderItem> list, String customer, LocalDate pickupDate) {
    OrderRequest order = new OrderRequest(list, customer, pickupDate);
    try {
      return orderClient.processOrder(order);
    } catch (FeignException.BadRequest e) {
      throw new RuntimeException("Errore di validazione");
    } catch (Exception e) {
      throw new RuntimeException("Something not good happened");
    }
  }

  public List<BatchDashboard> getBatchDashboard() {
    List<Batch> availableBatches = batchRepository.findByStatus("AVAILABLE");

    return availableBatches.stream()
        .map(BatchDashboard::new)
        .collect(Collectors.toList());
  }

}
