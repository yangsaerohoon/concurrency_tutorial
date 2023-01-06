package dev.ys.stockservice.service;

import dev.ys.stockservice.domain.Product;
import dev.ys.stockservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void sold(String productId, Integer quantity) {
        Product product = getProductByProductId(productId);
        product.sold(quantity);
    }

    public Product getProductByProductId(String productId) {
        return productRepository.findProductByProductId(productId);
    }
}
