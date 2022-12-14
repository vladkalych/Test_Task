package com.test_task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/accessDenied")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(302));
    }
}