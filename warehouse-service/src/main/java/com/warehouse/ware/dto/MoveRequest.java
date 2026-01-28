package com.warehouse.ware.dto;

public class MoveRequest {
  private String batchId;
  private String newLocation;
  private String workerId;

  public String getWorkerId() {
    return workerId;
  }

  public void setWorkerId(String workerId) {
    this.workerId = workerId;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getNewLocation() {
    return newLocation;
  }

  public void setNewLocation(String newLocation) {
    this.newLocation = newLocation;
  }

}
