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

    private final String SHOP_NAME = "TEST_SHOP";
    private final String SHOP_ADDRESS = "1234567890qwertyuiopasdfghjklzxcvbnm";

    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void init(){

        Product product = new Product();
        product.setName("TEST_PRODUCT");
        product.setDescription("123456789qwertyuiopasdfghjklzxcvbnm");
        product.setPrice(20d);

        Shop shop = new Shop();
        shop.setName(SHOP_NAME);
        shop.setAddress(SHOP_ADDRESS);
        shop.setProducts(Set.of(product));

        shopRepository.save(shop);
    }

    @Test
    void findShopByName() {
        Shop shop = shopRepository.findShopByName(SHOP_NAME);
        assertEquals(SHOP_ADDRESS, shop.getAddress());
    }
}