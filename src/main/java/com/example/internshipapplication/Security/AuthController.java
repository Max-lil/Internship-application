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
    public Map<String, Object> me(Authentication auth) {
        List<String> roles = auth.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        return Map.of(
                "email", auth.getName(),
                "roles", roles
        );
    }
}
