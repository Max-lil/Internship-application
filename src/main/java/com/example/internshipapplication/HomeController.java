package com.example.internshipapplication;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication auth) {
        return "Välkommen " + (auth != null ? auth.getName() : "gäst") + "!";
    }
}
