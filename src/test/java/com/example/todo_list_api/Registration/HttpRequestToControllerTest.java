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
import org.springframework.test.web.servlet.MvcResult;

import com.example.todo_list_api.User.UserController;
import com.example.todo_list_api.User.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestToControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnString() throws Exception
    {
        UserRegistrationRequest newUser = new UserRegistrationRequest("John Wick", "John.Wick@gmail.com", "revenge");

        // this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser))).andDo(print())
        MvcResult mvcResult = this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser))).andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    }

    // additional tests:
    // email is empty string
}
