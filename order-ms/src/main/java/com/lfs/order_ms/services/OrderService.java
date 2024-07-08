package com.lfs.order_ms.services;

import com.lfs.order_ms.model.dtos.BaseResponse;
import com.lfs.order_ms.model.dtos.OrderItemsRequest;
import com.lfs.order_ms.model.dtos.OrderRequest;
import com.lfs.order_ms.model.entities.Order;
import com.lfs.order_ms.model.entities.OrderItems;
import com.lfs.order_ms.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void placeOrder(OrderRequest orderRequest) {

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        if (result != null && result.hasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream().map(orderItemsRequest -> mapOrderItemsRequestToOrderItems(orderItemsRequest, order))
                    .toList());
            this.orderRepository.save(order);
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

    public List<OrderRequest> getAllOrder() {
        var orders = orderRepository.findAll();
        return orders.stream().map(this::mapOrderToOrderRequest).toList();
    }

    private OrderRequest mapOrderToOrderRequest(Order order) {
        return null;
    }
}
