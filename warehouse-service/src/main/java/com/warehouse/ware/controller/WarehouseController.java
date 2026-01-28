package com.warehouse.ware.controller;

import com.warehouse.ware.service.WarehouseService;
import com.warehouse.ware.repository.*;
import com.warehouse.ware.entity.*;
import com.warehouse.ware.dto.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

  private final WarehouseService warehouseService;

  @Autowired
  public WarehouseController(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @PostMapping("/receive")
  public ResponseEntity<?> receive(@RequestBody BatchReceiptRequest batchRequest) {
    try {
      BatchReceiptResponse batchResponse = warehouseService.receiveGoods(batchRequest);
      return ResponseEntity.ok(batchResponse);
    } catch (RuntimeException e) {
      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Non autorizzato");
      errorDetails.put("messege", e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
  }

  @PostMapping("/move")
  public ResponseEntity<?> move(@RequestBody MoveRequest moveRequest) {
    try {
      MoveResponse moveResponse = warehouseService.moveBatch(moveRequest.getBatchId(), moveRequest.getNewLocation(),
          moveRequest.getWorkerId());
      return ResponseEntity.ok(moveResponse);
    } catch (RuntimeException e) {
      Map<String, String> errorDetails = new HashMap<>();
      errorDetails.put("status", "Non autorizzato");
      errorDetails.put("messege", e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

  }
}
