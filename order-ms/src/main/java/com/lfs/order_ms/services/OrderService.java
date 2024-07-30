package com.lfs.order_ms.services;

import com.lfs.order_ms.events.OrderEvent;
import com.lfs.order_ms.model.dtos.*;
import com.lfs.order_ms.model.entities.Order;
import com.lfs.order_ms.model.entities.OrderItems;
import com.lfs.order_ms.model.enums.OrderStatus;
import com.lfs.order_ms.repositories.OrderRepository;
import com.lfs.order_ms.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final WebClient.Builder webClientBuilder;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("lb://inventory-ms/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        if (result != null && !result.hasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream().map(orderItemsRequest -> mapOrderItemsRequestToOrderItems(orderItemsRequest, order))
                    .toList());
            var savedOrder = this.orderRepository.save(order);
            this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
               new OrderEvent(savedOrder.getOrderNumber(), savedOrder.getOrderItems().size(), OrderStatus.PLACED)
            ));
            return mapOrderToOrderResponse(savedOrder);
        } else {
            throw new IllegalStateException("Invalid order");
        }
    }

    private OrderItems mapOrderItemsRequestToOrderItems(OrderItemsRequest orderItemsRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemsRequest.getId())
                .sku(orderItemsRequest.getSku())
                .price(orderItemsRequest.getPrice())
                .quantity(orderItemsRequest.getQuantity())
                .order(order)
                .build();
    }

    public List<OrderResponse> getAllOrder() {
        var orders = orderRepository.findAll();
        return orders.stream().map(this::mapOrderToOrderResponse).toList();
    }

    private OrderResponse mapOrderToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getOrderItems().stream().map(this::mapToOrderItemsResponse).toList());
    }

    private OrderItemsResponse mapToOrderItemsResponse(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }
}
