package com.test_task.repository;

import com.test_task.entity.Product;
import com.test_task.entity.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    private final String PRODUCT_NAME_A = "TEST_PRODUCT";
    private final String PRODUCT_DESCRIPTION_A = "qwertyuiopasdfghjklzxcvbnm";
    private final String PRODUCT_DESCRIPTION_B = "mnbvcxzlkjhgfdsapoiuytrewq";



    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void init(){
        Shop shop = new Shop();
        shop.setName("TEST_SHOP");
        shop.setAddress("qwertyuiopasdfghjklzxcvbnm");

        Product product = new Product();
        product.setName(PRODUCT_NAME_A);
        product.setDescription(PRODUCT_DESCRIPTION_A);
        product.setShop(shop);

        Product product_1 = new Product();
        product_1.setName("TEST_PRODUCT_1");
        product_1.setDescription(PRODUCT_DESCRIPTION_B);
        product_1.setShop(shop);

        shop.setProducts(Set.of(product, product_1));

        productRepository.save(product);
        productRepository.save(product_1);
    }

    @Test
    void findProductByName() {
        Product test_product = productRepository.findProductByName(PRODUCT_NAME_A);
        assertEquals(test_product.getDescription(), PRODUCT_DESCRIPTION_A);
    }

    @Test
    void findAllByShopId() {
        List<Product> productList = productRepository.findAllByShopId(1L);
        Product actualProductA = productList.get(0);
        Product actualProductB = productList.get(1);

        assertEquals(PRODUCT_DESCRIPTION_A, actualProductA.getDescription());
        assertEquals(PRODUCT_DESCRIPTION_B, actualProductB.getDescription());
    }
}