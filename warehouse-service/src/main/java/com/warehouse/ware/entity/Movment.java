package com.warehouse.ware.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "movements")
public class Movment {
  @Id
  private String id;

  private String batchId;
  private String oldLocation;
  private String newLocation;
  private String workerId;
  private LocalDateTime timeStamp;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }

  public String getOldLocation() {
    return oldLocation;
  }

  public void setOldLocation(String oldLocation) {
    this.oldLocation = oldLocation;
  }

  public String getNewLocation() {
    return newLocation;
  }

  public void setNewLocation(String newLocation) {
    this.newLocation = newLocation;
  }

  public String getWorkerId() {
    return workerId;
  }

  public void setWorkerId(String workerId) {
    this.workerId = workerId;
  }

  public Movment(String batchId, String oldLocation, String newLocation, String workerId,
      LocalDateTime timeStamp) {
    this.setBatchId(batchId);
    this.setOldLocation(oldLocation);
    this.setNewLocation(newLocation);
    this.setWorkerId(workerId);
    this.setTimeStamp(timeStamp);
  }

}
