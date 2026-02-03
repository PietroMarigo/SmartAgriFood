package com.warehouse.office.controller;

import com.warehouse.common.dto.OrderResponse;
import com.warehouse.office.dto.BatchDashboard;
import com.warehouse.common.dto.OrderRequest;
import com.warehouse.office.service.OfficeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/office")
public class OfficeController {

  private final OfficeService officeService;

  public OfficeController(OfficeService officeService) {
    this.officeService = officeService;
  }

  @GetMapping("/dashboard")
  public List<BatchDashboard> getDashboard() {
    return officeService.getBatchDashboard();
  }

  @PostMapping("/submit-order")
  public ResponseEntity<?> submitOrder(@RequestBody OrderRequest request) {
    try {
      OrderResponse response = officeService.createOrderList(
          request.getItems(),
          request.getCustomer(),
          request.getPickupDate());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
