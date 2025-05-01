package com.example.todo_list_api.Todo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {
    private static final String END_POINT_PATH = "/todos";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTodoItem() throws Exception
    {
        Todo todo = new Todo("grocery", "milk and eggs");

        this.mockMvc.perform(post(END_POINT_PATH).header("Authorization", "brFRm7PDTmAJYOMKStlhk2cd5").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))).andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTodoItem() throws Exception
    {
        Todo todo = new Todo("grocery", "bread and ice cream");

        long id = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH + "/{id}", id).header("Authorization", "brFRm7PDTmAJYOMKStlhk2cd5").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))).andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteTodoItem() throws Exception
    {
        long id = 1;
        this.mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/{id}", id).header("Authorization", "brFRm7PDTmAJYOMKStlhk2cd5")).andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTodoItems() throws Exception
    {
        this.mockMvc.perform(get(END_POINT_PATH).header("Authorization", "brFRm7PDTmAJYOMKStlhk2cd5")).andDo(print()).andExpect(status().isOk());
    }
}
