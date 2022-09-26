package com.test_task.service;

import com.test_task.entity.Product;
import com.test_task.entity.Shop;
import com.test_task.repository.ProductRepository;
import com.test_task.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final String MOCK_NAME = "admin";
    private static final String MOCK_DESCRIPTION = "12345678909876543211234567890_DESCRIPTION";
    private static final String MOCK_ADDRESS = "12345678909876543211234567890_ADDRESS";
    private static final long MOCK_ID = 77;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void save() {
        Product product = new Product();
        product.setName(MOCK_NAME);
        product.setDescription(MOCK_DESCRIPTION);

        when(productRepository.save(product)).thenReturn(product);

        assertDoesNotThrow(() -> productService.save(product));
    }

    @Test
    void deleteById() {
        doThrow(EntityNotFoundException.class).when(productRepository).deleteById(MOCK_ID);

        assertThrows(EntityNotFoundException.class, () -> productService.deleteById(MOCK_ID));
    }

    @Test
    void edit() {
        Product product = new Product();
        product.setName(MOCK_NAME);
        product.setDescription(MOCK_DESCRIPTION);

        when(productRepository.save(product)).thenReturn(product);

        assertDoesNotThrow(() -> productService.edit(product));
    }

    @Test
    void findAll() {
        Product product = new Product();
        product.setName(MOCK_NAME);
        product.setDescription(MOCK_DESCRIPTION);

        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> actualShops = productService.findAll();

        assertEquals(products.size(), actualShops.size());
    }

    @Test
    void findByShopId() {
        Shop shop = new Shop();
        shop.setName(MOCK_NAME);
        shop.setAddress(MOCK_ADDRESS);
        shop.setId(MOCK_ID);

        Product expectedProduct = new Product();
        expectedProduct.setName(MOCK_NAME);
        expectedProduct.setDescription(MOCK_DESCRIPTION);

        when(productRepository.findAllByShopId(MOCK_ID)).thenReturn(List.of(expectedProduct));

        List<Product> actualProducts = productService.findByShopId(MOCK_ID);

        assertEquals(expectedProduct.getName(), actualProducts.get(0).getName());
    }

    @Test
    void findById() {
        Product expectedProduct = new Product();
        expectedProduct.setName(MOCK_NAME);
        expectedProduct.setDescription(MOCK_DESCRIPTION);
        expectedProduct.setId(MOCK_ID);

        when(productRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productService.findById(MOCK_ID);
        assertEquals(expectedProduct.getId(), actualProduct.getId());
    }
}