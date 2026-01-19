package com.warehouse.ware.repository;

import com.warehouse.ware.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
  Optional<Products> findBySku(String sku);

  boolean existsBySku(String sku);
}
