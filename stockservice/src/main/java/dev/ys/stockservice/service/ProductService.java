package dev.ys.stockservice.service;

import dev.ys.stockservice.domain.Product;

public interface ProductService {
    void sold(String productId, Integer quantity);

    Product getProductByProductId(String productId);
}
