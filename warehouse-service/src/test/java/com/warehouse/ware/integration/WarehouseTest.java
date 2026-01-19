package com.warehouse.ware.integration;

import com.warehouse.ware.WarehouseServiceApplication;
import com.warehouse.ware.entity.Batch;
// import com.warehouse.ware.entity.Products;
import com.warehouse.ware.exception.ProductNotFoundException;
import com.warehouse.ware.service.WarehouseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = WarehouseServiceApplication.class)
@ActiveProfiles("test")
@Sql(scripts = "classpath:products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WarehouseTest {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private WarehouseService warehouseService;

  @Test
  @Order(1)
  public void testWarehouseSql() throws Exception {
    assertNotNull(dataSource, "DataSource è vuota, no database");

    try (Connection connection = dataSource.getConnection()) {
      assertNotNull(connection, "Non c'è connessione");
      assertTrue(connection.isValid(2), "La connessione non è valida");
      System.out.println("Siamo connessi al db prodotti SQLite");
    }

    assertThrows(
        ProductNotFoundException.class,
        () -> {
          warehouseService.returnProduct("");
        },
        "In teoria non dovrebbe trovare un prodotto");

    assertNotNull(warehouseService.returnProduct("A001"), "Non è stato trovato il prodotto");
  }

  @Test
  @Order(2)
  public void testWarehouseNoSql() {
    String sku = "A001";
    Double weight = 150.5;
    String location = "Z-A-001";
    Batch savedBatch = warehouseService.receiveGoods(sku, weight, location);
    assertNotNull(savedBatch.getId());
    LocalDate expectedDate = LocalDate.now().plusDays(30);
    assertEquals(expectedDate, savedBatch.getExpiryDate());
  }
}
