package com.warehouse.ware.dto;

public class MoveRequest {
  private String batchId;
  private String newZone;
  private String workerUsername;

  public String getWorkerUsername() {
    return workerUsername;
  }

  public void setWorkerUsername(String workerUsername) {
    this.workerUsername= workerUsername;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getNewZone() {
    return newZone;
  }

  public void setNewZone(String newZone) {
    this.newZone= newZone;
  }

}
