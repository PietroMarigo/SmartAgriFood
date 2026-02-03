package com.warehouse.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import com.warehouse.common.dto.OrderRequest;
import com.warehouse.common.dto.OrderResponse;
import com.warehouse.order.service.OrderService;
import com.warehouse.common.repository.OrderRepository;
import com.warehouse.common.entity.Order;
import com.warehouse.common.entity.OrderStatus;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService service;
  private final OrderRepository repository;

  public OrderController(OrderService service, OrderRepository repository) {
    this.service = service;
    this.repository = repository;
  }

  @PostMapping("/process")
  public OrderResponse processOrder(@RequestBody OrderRequest request) {
    return service.processOrder(request);
  }

  @GetMapping("/pending")
  public List<Order> getPendingOrders() {
    return repository.findByStatus("NOT_PROCESSED");
  }

  @GetMapping("/{id}")
  public Order getOrder(@PathVariable String id) {
    return repository.findById(id).orElseThrow();
  }

  @PutMapping("/{id}/start")
  public ResponseEntity<?> startPicking(@PathVariable String id, @RequestParam String worker) {
    Order order = repository.findById(id).orElseThrow();
    order.setStatus(OrderStatus.IN_PROCESS);
    order.setWorkerUsername(worker); // Track who is doing the work
    repository.save(order);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/complete")
  public ResponseEntity<?> completeOrder(@PathVariable String id, @RequestBody Map<String, String> body) {
    Order order = repository.findById(id).orElseThrow();
    order.setStatus(OrderStatus.PROCESSED);
    order.setLoadingZone(body.get("loadingZone"));
    repository.save(order);
    return ResponseEntity.ok().build();
  }
}
