package com.example.todo_list_api.Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todo_list_api.Authentication.AuthenticationController;
import com.example.todo_list_api.Authentication.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldAuthenticateUser() throws Exception
    {
        Authentication newAuth = new Authentication("email@email.com", "email");

        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newAuth))).andDo(print())
        .andExpect(status().isOk());
    }

    // additional tests with Postman
    // 1. incorrect email: return Unauthorized status
    // 2. incorrect password: return Unauthorized status
}
