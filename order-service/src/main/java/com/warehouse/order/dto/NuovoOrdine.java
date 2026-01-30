package com.warehouse.order.dto;

public class NuovoOrdine {

  private String prodotto; // productSku
  private double quantitaKg;

  public NuovoOrdine() {
  }

  public NuovoOrdine(String prodotto, double quantitaKg) {
    this.prodotto = prodotto;
    this.quantitaKg = quantitaKg;
  }

  public String getProdotto() {
    return prodotto;
  }

  public void setProdotto(String prodotto) {
    this.prodotto = prodotto;
  }

  public double getQuantitaKg() {
    return quantitaKg;
  }

  public void setQuantitaKg(double quantitaKg) {
    this.quantitaKg = quantitaKg;
  }
}
