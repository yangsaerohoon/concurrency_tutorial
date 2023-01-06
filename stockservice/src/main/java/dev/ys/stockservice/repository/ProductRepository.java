package dev.ys.stockservice.repository;

import dev.ys.stockservice.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductId(String productId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Product findPessimisticLockProductByProductId(String productId);
}
