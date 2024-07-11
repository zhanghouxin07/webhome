package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "{\"truth\":\"爱你胖胖\",\"time\": \"一万年\",\"city\":\"Everywhere\"}";
    }
}