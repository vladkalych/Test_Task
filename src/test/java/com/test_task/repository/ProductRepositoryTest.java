package com.test_task.repository;

import com.test_task.entity.Product;
import com.test_task.entity.Shop;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void init(){
        Shop shop = new Shop();
        shop.setName("TEST_SHOP");
        shop.setAddress("qwertyuiopasdfghjklzxcvbnm");

        Product product = new Product();
        product.setName("TEST_PRODUCT");
        product.setDescription("qwertyuiopasdfghjklzxcvbnm");
        product.setShop(shop);

        Product product_1 = new Product();
        product_1.setName("TEST_PRODUCT_1");
        product_1.setDescription("mnbvcxzlkjhgfdsapoiuytrewq");
        product_1.setShop(shop);

        shop.setProducts(Set.of(product, product_1));

        productRepository.save(product);
        productRepository.save(product_1);
    }

    @Test
    void findProductByName() {
        final String trueDescription = "qwertyuiopasdfghjklzxcvbnm";
        Product test_product = productRepository.findProductByName("TEST_PRODUCT");
        assertEquals(test_product.getDescription(), trueDescription);
    }

    @Test
    void findAllByShopId() {
        final String descriptionFirst = "qwertyuiopasdfghjklzxcvbnm";
        final String descriptionSecond = "mnbvcxzlkjhgfdsapoiuytrewq";

        List<Product> productList = productRepository.findAllByShopId(1L);
        Product product = productList.get(0);
        Product product_1 = productList.get(1);

        assertEquals(product.getDescription(), descriptionFirst);
        assertEquals(product_1.getDescription(), descriptionSecond);
    }
}