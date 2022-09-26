package com.test_task.service;

import com.test_task.entity.Role;
import com.test_task.entity.Shop;
import com.test_task.entity.User;
import com.test_task.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    private static final String MOCK_NAME = "admin";
    private static final String MOCK_ADDRESS = "12345678909876543211234567890";
    private static final long MOCK_ID = 77;

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Test
    void save() {
        Shop shop = new Shop();
        shop.setName(MOCK_NAME);
        shop.setAddress(MOCK_ADDRESS);

        when(shopRepository.save(shop)).thenReturn(shop);

        assertDoesNotThrow(() -> shopService.save(shop));
    }

    @Test
    void deleteById() {
        doThrow(EntityNotFoundException.class).when(shopRepository).deleteById(MOCK_ID);

        assertThrows(EntityNotFoundException.class, () -> shopService.deleteById(MOCK_ID));
    }

    @Test
    void edit() {
        Shop shop = new Shop();
        shop.setName(MOCK_NAME);
        shop.setAddress(MOCK_ADDRESS);

        when(shopRepository.save(shop)).thenReturn(shop);

        assertDoesNotThrow(() -> shopService.edit(shop));
    }

    @Test
    void findAll() {
        Shop shop = new Shop();
        shop.setName(MOCK_NAME);
        shop.setAddress(MOCK_ADDRESS);

        List<Shop> shops = new ArrayList<>();
        shops.add(shop);
        when(shopRepository.findAll()).thenReturn(shops);

        List<Shop> actualShops = shopService.findAll();

        assertEquals(shops.size(), actualShops.size());
    }

    @Test
    void findById() {
        Shop expectedShop = new Shop();
        expectedShop.setName(MOCK_NAME);
        expectedShop.setAddress(MOCK_ADDRESS);

        expectedShop.setId(MOCK_ID);
        when(shopRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedShop));

        Shop actualShop = shopService.findById(MOCK_ID);

        assertEquals(expectedShop.getId(), actualShop.getId());
    }

    @Test
    void findByName() {
        Shop expectedShop = new Shop();
        expectedShop.setName(MOCK_NAME);
        expectedShop.setAddress(MOCK_ADDRESS);

        when(shopRepository.findShopByName(MOCK_NAME)).thenReturn(expectedShop);

        Shop actualShop = shopService.findByName(expectedShop.getName());

        assertEquals(expectedShop.getName(), actualShop.getName());
    }
}