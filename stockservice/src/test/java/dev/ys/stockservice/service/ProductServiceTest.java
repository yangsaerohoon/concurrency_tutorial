package dev.ys.stockservice.service;

import dev.ys.stockservice.domain.Product;
import dev.ys.stockservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Qualifier("productServiceImpl")
    @Autowired
    private ProductService productService;

    @Qualifier("productServicePessimisticLockImpl")
    @Autowired
    private ProductService pessimisticLockProductService;

    @Autowired
    private ProductRepository productRepository;

    private Product originProduct;
    @BeforeEach
    void init() {
        originProduct = productRepository.saveAndFlush(
                new Product("productA", 100));
    }

    @Test
    void soldTest() {
        productService.sold("productA", 10);
        Product soldProduct = productService.getProductByProductId("productA");

        assertThat(originProduct.getQuantity()-10).isEqualTo(soldProduct.getQuantity());
    }

    @Test
    void multipleSoldTest() throws InterruptedException {
        int numOfThreads = 10;
        //10개 스레드의 스레드풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        //여러 스레드의 종료를 기다리기 위해 사용
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(() -> {
                productService.sold("productA", 10);
                //latch를 하나씩 줄임
                latch.countDown();
            });
        }
        //latch가 0이 되면 스레드가 모두 종료한 것
        latch.await();

        Product soldProduct = productService.getProductByProductId("productA");

        assertThat(soldProduct.getQuantity()).isEqualTo(0);
    }

    @Test
    void pessimisticLockSoldTest() {
        pessimisticLockProductService.sold("productA", 10);
        Product soldProduct = pessimisticLockProductService.getProductByProductId("productA");

        assertThat(originProduct.getQuantity()-10).isEqualTo(soldProduct.getQuantity());
    }

    @Test
    void pessimisticLockMultipleSoldTest() throws InterruptedException {
        int numOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            executorService.submit(() -> {
                pessimisticLockProductService.sold("productA", 10);
                latch.countDown();
            });
        }
        latch.await();

        Product soldProduct = productService.getProductByProductId("productA");

        assertThat(soldProduct.getQuantity()).isEqualTo(0);
    }
}