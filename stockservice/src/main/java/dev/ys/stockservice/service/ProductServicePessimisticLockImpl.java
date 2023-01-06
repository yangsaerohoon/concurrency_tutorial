package dev.ys.stockservice.service;

import dev.ys.stockservice.domain.Product;
import dev.ys.stockservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServicePessimisticLockImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServicePessimisticLockImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void sold(String productId, Integer quantity) {
        Product product = getProductByProductId(productId);
        product.sold(quantity);
        productRepository.saveAndFlush(product);
    }

    public Product getProductByProductId(String productId) {
        return productRepository.findPessimisticLockProductByProductId(productId);
    }
}
