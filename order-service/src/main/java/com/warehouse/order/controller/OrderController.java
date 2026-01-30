package com.warehouse.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.order.dto.PickingTask;
import com.warehouse.common.entity.Order;
import com.warehouse.order.service.OrderService;

@RestController
@RequestMapping("/office/orders")
public class OrderController {

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @PostMapping("/process")
  public List<PickingTask> processOrder(@RequestBody Order order) {
    return service.processOrder(order);
  }
}
