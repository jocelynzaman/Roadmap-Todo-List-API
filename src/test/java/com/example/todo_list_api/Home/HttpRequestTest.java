package com.example.todo_list_api.Home;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/*
 * start application and listen for connection
 * send HTTP request and assert response
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test 
    void greetingShouldReturnDefaultMessage() throws Exception
    {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).isEqualTo("Hello, World");
    }
}
