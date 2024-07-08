package com.lfs.order_ms.controllers;

import com.lfs.order_ms.model.dtos.OrderRequest;
import com.lfs.order_ms.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OrderRequest> getAllOrder(){
        return this.orderService.getAllOrder();
    }
}
