package com.lfs.notification_service.events;

import com.lfs.notification_service.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
