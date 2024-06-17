package com.example.instagram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controler {

    @GetMapping("/")
    public String root() {
        return "login";
    }
    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}
