package com.example.todo_list_api.Home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
    
}
