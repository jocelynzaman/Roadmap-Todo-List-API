package com.example.todo_list_api.Registration;

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

import com.example.todo_list_api.Authentication.AuthenticationService;
import com.example.todo_list_api.User.UserController;
import com.example.todo_list_api.User.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(RegisterControllerTest.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
    private static final String END_POINT_PATH = "/register";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationService authService;

    @Test
    public void shouldCreateNewUser() throws Exception
    {
        UserRegistrationRequest newUser = new UserRegistrationRequest("John Wick", "John.Wick@gmail.com", "revenge");

        this.mockMvc.perform(post(END_POINT_PATH).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isOk())
        .andDo(print());
    }

    // additional tests for Postman
    // 1. blank name, email, and password: return status of bad request
}
