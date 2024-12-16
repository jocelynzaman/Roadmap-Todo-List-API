package com.example.todo_list_api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo_list_api.Home.HomeController;

//simple sanity check for application context activation
@SpringBootTest
class SmokeTest {
    @Autowired
    private HomeController controller;

    @Test
    void contextLoads() throws Exception
    {
        assertThat(controller).isNotNull();
    }
}
