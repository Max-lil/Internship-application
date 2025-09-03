package com.example.internshipapplication;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TestSecurityController {

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/user/hello-test") // <-- ändrad från /user/hello
    public String helloStudent() {
        return "Hej student! Du är inloggad och har rollen STUDENT.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/secret")
    public String adminOnly() {
        return "Endast admin får se detta.";
    }

    @GetMapping("/public/info")
    public String publicInfo() {
        return "Detta är en publik endpoint – kräver ingen inloggning.";
    }
}
