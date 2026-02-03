package com.warehouse.office.client;

import com.warehouse.common.dto.OrderRequest;
import com.warehouse.common.dto.OrderResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "http://order-service:8083")
public interface OrderClient {
  @PostMapping("/api/orders/process")
  OrderResponse processOrder(@RequestBody OrderRequest orderRequest);
}
