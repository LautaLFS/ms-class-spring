package com.lfs.order_ms.model.dtos;

public record OrderItemsResponse(String id, String sku, Double price, Long quantity) {
}
