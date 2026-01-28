package com.warehouse.ware.integration;

import com.warehouse.ware.WarehouseServiceApplication;
import com.warehouse.ware.entity.Batch;
import com.warehouse.ware.entity.Movment;
// import com.warehouse.ware.entity.Products;
import com.warehouse.ware.exception.ProductNotFoundException;
import com.warehouse.ware.service.WarehouseService;
import com.warehouse.ware.dto.*;

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
    BatchReceiptRequest batchRequest = new BatchReceiptRequest();
    batchRequest.setBatchId("A001-003-20250105-001");
    batchRequest.setWeight(150.5);
    batchRequest.setLocationCode("A-001");
    BatchReceiptResponse savedBatch = warehouseService.receiveGoods(batchRequest);
    assertNotNull(savedBatch.getBatchId());
    LocalDate expectedDate = LocalDate.now().plusDays(30);
    assertEquals(expectedDate, savedBatch.getExpiryDate());
  }

  @Test
  @Order(3)
  public void testMovement() {
    String batchId = "A001-003-20250105-001";
    String newZone = "Z-B-002";
    String workerUsername = "MR001";
    MoveResponse savedMovement = warehouseService.moveBatch(batchId, newZone, workerUsername);
    assertNotNull(savedMovement.getBatchId());
  }
}
