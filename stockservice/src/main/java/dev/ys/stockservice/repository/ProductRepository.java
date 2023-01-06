package dev.ys.stockservice.repository;

import dev.ys.stockservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductId(String productId);
}
