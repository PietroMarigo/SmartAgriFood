package com.warehouse.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Products {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String sku;

  private String name;

  @Column(name = "shelf_life_days")
  private Integer shelfLifeDays;

  @Enumerated(EnumType.STRING)
  private Categories category;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getShelfLifeDays() {
    return shelfLifeDays;
  }

  public void setShelfLifeDays(Integer shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
  }

  public Categories getCategory() {
    return category;
  }

  public void setCategory(Categories category) {
    this.category = category;
  }

  public String toString() {
    return "Prodotto{" +
        "id=" + id +
        ", sku='" + sku + '\'' +
        ", nome='" + name + '\'' +
        ", durata='" + shelfLifeDays + '\'' +
        ", categoria='" + category + '\'' +
        '}';
  }

}
