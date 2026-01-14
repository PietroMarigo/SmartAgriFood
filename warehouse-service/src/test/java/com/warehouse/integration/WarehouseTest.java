package com.warehouse.integration;

import com.warehouse.WarehouseServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = WarehouseServiceApplication.class)
public class WarehouseTest {

  @Test
  public void testWarehouse() {
    System.out.println("Ciao");
  }
}
