package com.test_task.controller;

import com.test_task.dto.ProductDto;
import com.test_task.dto.ShopDto;
import com.test_task.repository.ShopRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShopsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopRepository shopRepository;
    private final String SHOP_NAME = "TEST_SHOP_1";
    private Long shopId;

    @Test
    @Order(1)
    void chain() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"));
    }

    @Test
    @Order(2)
    void getAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/add").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDto"));
    }

    @Test
    @Order(3)
    void addShop() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(SHOP_NAME);
        shopDto.setAddress("1234567890qwertyuiopasdfghjklzxcvbnm");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .post("/chain/add/").with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .flashAttr("shopDto", shopDto)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                );
    }

    @Test
    @Order(4)
    void getEditForm() throws Exception {
        shopId = shopRepository.findShopByName(SHOP_NAME).getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/edit/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    @Order(5)
    void editShop() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName("TEST_PRODUCT_EDITED");
        shopDto.setAddress("mnbvcxzlkjhgfdsapoiuytrewq");

        shopId = shopRepository.findShopByName(SHOP_NAME).getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .patch("/chain/edit/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .flashAttr("shopDto", shopDto)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                );
    }

    @Test
    @Order(6)
    void deleteShop() throws Exception {

        String SHOP_NAME_EDITED = "TEST_PRODUCT_EDITED";
        shopId = shopRepository.findShopByName(SHOP_NAME_EDITED).getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .delete("/chain/delete/{id}", shopId).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(model().attributeExists("shopDtoList"))
                );
    }
}