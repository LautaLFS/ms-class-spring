package com.lfs.order_ms.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsRequest {

    private String id;
    private String sku;
    private Double price;
    private Long quantity;
}
