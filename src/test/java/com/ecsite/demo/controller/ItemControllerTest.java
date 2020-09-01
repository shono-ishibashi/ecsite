package com.ecsite.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class ItemControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ItemController controller;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void orderItemFormSetUp() {
    }

    @Test
    void showList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("item_list_noodle"));
    }

    @Test
    void showDetail() {
    }

    @Test
    void search() {
    }
}