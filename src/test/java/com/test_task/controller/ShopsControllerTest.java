package com.test_task.controller;

import com.test_task.dto.ProductDto;
import com.test_task.dto.ShopDto;
import org.junit.jupiter.api.Test;
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
class ShopsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void chain() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"));
    }

    @Test
    void getAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/add").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDto"));
    }

    @Test
    void addShop() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName("TEST_SHOP_1");
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
    void getEditForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/edit/{id}", 14).with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    void editShop() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName("TEST_PRODUCT_EDITED");
        shopDto.setAddress("mnbvcxzlkjhgfdsapoiuytrewq");

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .patch("/chain/edit/{id}", 17).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .flashAttr("shopDto", shopDto)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                );
    }

    @Test
    void deleteShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/chain/shops").with(user("admin").password("user11").roles("USER", "ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shopDtoList"))

                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders
                                        .delete("/chain/delete/{id}", 15).with(user("admin").password("user11").roles("USER", "ADMIN"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(model().attributeExists("shopDtoList"))
                );
    }
}