package com.warehouse.ware.dto;

import com.warehouse.ware.entity.Movment;

public class MoveResponse {
  private Movment move;

  public Movment getMove() {
    return move;
  }

  public void setMove(Movment move) {
    this.move = move;
  }

  public MoveResponse(Movment move) {
    this.move = move;
  }

  public String getBatchId() {
    return move.getBatchId();
  }

}
