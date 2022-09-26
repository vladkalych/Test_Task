package com.test_task.controller;

import com.test_task.dto.ProductDto;
import com.test_task.dto.ShopDto;
import com.test_task.entity.Product;
import com.test_task.entity.Shop;
import com.test_task.mapper.ProductMapper;
import com.test_task.mapper.ShopMapper;
import com.test_task.repository.ProductRepository;
import com.test_task.repository.ShopRepository;
import com.test_task.service.ProductService;
import com.test_task.service.ShopService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    private final String SHOP_NAME = "TEST_SHOP_1";
    private final String SHOP_ADDRESS = "1234567890qwertyuiopasdfghjklzxcvbnm";
    private final String PRODUCT_NAME = "PRODUCT_TEST_1";
    private final String PRODUCT_DESCRIPTION = "MNBVCXZLKJHGFDSAPOIUYTREWQ";
    private final Double PRODUCT_PRICE = 300d;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;


    private Long shopId;

    @BeforeEach
    void init() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(SHOP_NAME);
        shopDto.setAddress(SHOP_ADDRESS);
        Shop shop = ShopMapper.INSTANCE.toShop(shopDto);
        shopRepository.save(shop);
        shopId = shopRepository.findShopByName(SHOP_NAME).getId();
    }

    @AfterEach
    void destroy(){
        shopRepository.deleteById(shopId);
    }

    @Test
    void shop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/shop/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDtoList"));
    }

    @Test
    void getAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/add/").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    void addProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName(PRODUCT_NAME);
        productDto.setDescription(PRODUCT_DESCRIPTION);
        productDto.setPrice(PRODUCT_PRICE);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/shop/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .post("/products/add/").with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .flashAttr("productDto", productDto)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                );
    }

    @Test
    void getEditForm() throws Exception {
        Product product = productRepository.findProductByName(PRODUCT_NAME);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/edit/{id}", product.getId()).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    void editProduct() throws Exception {
        Product product = productRepository.findProductByName(PRODUCT_NAME);

        ProductDto productDtoTest = new ProductDto();
        productDtoTest.setName(PRODUCT_NAME);
        productDtoTest.setDescription(PRODUCT_DESCRIPTION);
        productDtoTest.setPrice(350d);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/shop/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .patch("/products/edit/{id}", product.getId()).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .flashAttr("productDto", productDtoTest)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                );
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = productRepository.findProductByName(PRODUCT_NAME);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/shop/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .delete("/products/delete/{id}", product.getId()).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(model().attributeExists("productDtoList"))
                );
    }
}