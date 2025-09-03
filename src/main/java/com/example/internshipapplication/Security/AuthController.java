package com.example.internshipapplication.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
public class AuthController {

    /*
    /me endpointen i din AuthController returnerar information om den för
    tillfället inloggade användaren. Den fungerar så här: output json objekt

    auth.getName() ger emailadressen (eftersom ni använder email som username),
    och auth.getAuthorities() ger rollerna som Spring Security har tilldelat baserat på er UserService.
    */

    @GetMapping("/me")
    public Map<String, Object> me(org.springframework.security.core.Authentication auth) {
        var roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority()).toList();
        return java.util.Map.of("email", auth.getName(), "roles", roles);
    }
}

