package com.example.internshipapplication.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> me(org.springframework.security.core.Authentication auth) {
        var roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority()).toList();
        return java.util.Map.of("email", auth.getName(), "roles", roles);
    }
}
