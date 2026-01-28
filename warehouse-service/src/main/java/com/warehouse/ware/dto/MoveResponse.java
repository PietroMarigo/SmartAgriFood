package com.warehouse.ware.dto;

import com.warehouse.ware.entity.Movment;
import java.time.LocalDateTime;

public class MoveResponse {
  private String batchId;
  private String oldLocation;
  private String newLocation;
  private String workerId;
  private LocalDateTime timeStamp;

  public MoveResponse(Movment move) {
    this.batchId = move.getBatchId();
    this.oldLocation = move.getOldLocation();
    this.newLocation = move.getNewLocation();
    this.workerId = move.getWorkerId();
    this.timeStamp = move.getTimeStamp();
  }

  // Add Getters for all 5 fields so Spring can serialize them to JSON
  public String getBatchId() {
    return batchId;
  }

  public String getOldLocation() {
    return oldLocation;
  }

  public String getNewLocation() {
    return newLocation;
  }

  public String getWorkerId() {
    return workerId;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }
}
