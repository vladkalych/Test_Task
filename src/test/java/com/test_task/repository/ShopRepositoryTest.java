package com.test_task.repository;

import com.test_task.entity.Product;
import com.test_task.entity.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShopRepositoryTest {

    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void init(){

        Product product = new Product();
        product.setName("TEST_PRODUCT");
        product.setDescription("123456789qwertyuiopasdfghjklzxcvbnm");
        product.setPrice(20d);

        Shop shop = new Shop();
        shop.setName("TEST_SHOP");
        shop.setAddress("1234567890qwertyuiopasdfghjklzxcvbnm");
        shop.setProducts(Set.of(product));

        shopRepository.save(shop);
    }

    @Test
    void findShopByName() {
        final String trueAddress = "1234567890qwertyuiopasdfghjklzxcvbnm";
        Shop shop = shopRepository.findShopByName("TEST_SHOP");
        assertEquals(trueAddress, shop.getAddress());
    }
}