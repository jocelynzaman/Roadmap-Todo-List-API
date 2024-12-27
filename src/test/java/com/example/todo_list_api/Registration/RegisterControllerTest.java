package com.example.todo_list_api.Registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.todo_list_api.Registration.RegistrationService;
import com.example.todo_list_api.Registration.UserRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(RegisterControllerTest.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateNewUser() throws Exception
    {
        UserRegistration newUser = new UserRegistration("John Wick", "John.Wick@gmail.com", "revenge");

        this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUser))).andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void shouldThrowMissingInput() throws Exception
    {
        UserRegistration newUser = new UserRegistration("John", "", "password");

        this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null))).andDo(print())
        // MvcResult mvcResult = this.mockMvc.perform(post("/register", exceptionParam).contentType(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isBadRequest());
        // .andReturn();
        // .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
        // assertEquals("hello", mvcResult.getResponse().toString());
    }
}
