package com.lfs.order_ms.events;

import com.lfs.order_ms.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
