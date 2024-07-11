package com.lfs.order_ms.model.dtos;

import java.util.List;

public record OrderResponse(String id, String orderNumber, List<OrderItemsResponse> orderItems) {
}
