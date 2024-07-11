package com.lfs.inventory_ms.services;

import com.lfs.inventory_ms.model.dtos.BaseResponse;
import com.lfs.inventory_ms.model.dtos.OrderItemRequest;
import com.lfs.inventory_ms.model.entities.Inventory;
import com.lfs.inventory_ms.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = this.inventoryRepository.findBySku(sku);
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.getSku())).findFirst();

            if (inventory.isPresent() && inventory.get().getQuantity() < orderItem.getQuantity()) {
                errorList.add(String.format("Not enough stock for %s, available: %d", orderItem.getSku(), inventory.get().getQuantity()));
            }
        });
        return !errorList.isEmpty() ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
