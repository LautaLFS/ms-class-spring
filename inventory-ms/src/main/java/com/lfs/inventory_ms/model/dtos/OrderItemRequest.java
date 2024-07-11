package com.lfs.inventory_ms.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private String id;
    private String sku;
    private Double price;
    private Long quantity;
}
