package com.lfs.inventory_ms.controllers;

import com.lfs.inventory_ms.model.dtos.BaseResponse;
import com.lfs.inventory_ms.model.dtos.OrderItemRequest;
import com.lfs.inventory_ms.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean isInStock(@PathVariable String sku){
        return inventoryService.isInStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItems){
        return this.inventoryService.areInStock(orderItems);
    }
}
