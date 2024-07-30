package com.lfs.notification_service.listeners;

import com.lfs.notification_service.events.OrderEvent;
import com.lfs.notification_service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "orders-topic")
    public void listen(String message) {
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);
        log.info("Order {} event received for order: {} whit {} items",
                orderEvent.orderNumber(),
                orderEvent.orderStatus(),
                orderEvent.itemsCount());
    }
}
