package com.lfs.product_ms.services;

import com.lfs.product_ms.model.dtos.ProductRequest;
import com.lfs.product_ms.model.dtos.ProductResponse;
import com.lfs.product_ms.model.entities.Product;
import com.lfs.product_ms.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest) {
        log.info("Adding product: {}", productRequest);
        var product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();
        productRepository.save(product);
        log.info("Product added successfully: {}", product);
    }

    public List<ProductResponse> getAllProducts(){
        var products = productRepository.findAll();
        return products.stream().map(this::matToProductResponse).toList();
    }

    private ProductResponse matToProductResponse(Product product) {
        return ProductResponse.builder()
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
