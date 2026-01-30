package com.warehouse.office.integration;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.warehouse.order.OrderServiceApplication;
import com.warehouse.order.dto.PickingTask;
import com.warehouse.common.entity.Batch;
import com.warehouse.common.entity.Order;
import com.warehouse.common.entity.OrderItem;
import com.warehouse.common.repository.BatchRepository;
import com.warehouse.order.service.OrderService;

@SpringBootTest(classes = OrderServiceApplication.class)
@ActiveProfiles("test")
public class OrderServiceTest {

  @Autowired
  private OrderService service;

  @Autowired
  private BatchRepository batchRepository;

  @Test
  public void shouldPickUsingFEFO() {

    batchRepository.deleteAll();

    Batch b1 = new Batch();
    b1.setBatchId("A001-001-20260101-001");
    b1.setProductSku("A-001");
    b1.setWeightKg(5.0);
    b1.setExpiryDate(LocalDate.now().plusDays(1));
    b1.setLocationCode("Z-A-001");
    b1.setStatus("AVAILABLE");

    Batch b2 = new Batch();
    b2.setBatchId("A001-001-20260101-002");
    b2.setProductSku("A-001");
    b2.setWeightKg(10.0);
    b2.setExpiryDate(LocalDate.now().plusDays(5));
    b2.setLocationCode("Z-A-002");
    b2.setStatus("AVAILABLE");

    batchRepository.save(b1);
    batchRepository.save(b2);

    Order order = new Order();
    order.setItems(List.of(
        new OrderItem("A-001", 8.0)));

    List<PickingTask> result = service.processOrder(order);

    assertThat(result).hasSize(2);
  }

}
