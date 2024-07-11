package com.lfs.inventory_ms.utils;

import com.lfs.inventory_ms.model.entities.Inventory;
import com.lfs.inventory_ms.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) {
        if(inventoryRepository.findAll().isEmpty()){
            log.info("No inventory data found. Creating some sample data.");
            inventoryRepository.saveAll(
                    List.of(Inventory.builder()
                                    .sku("ABC123")
                                    .quantity(100L)
                                    .build(),
                            Inventory.builder()
                                    .sku("XYZ789")
                                    .quantity(50L)
                                    .build(),
                            Inventory.builder()
                                    .sku("DEF456")
                                    .quantity(200L)
                                    .build(),
                            Inventory.builder()
                                    .sku("00009")
                                    .quantity(0L)
                                    .build())
            );
            log.info("Sample inventory data created successfully.");
        }
    }
}
