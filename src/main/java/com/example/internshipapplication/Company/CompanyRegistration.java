package com.example.internshipapplication.Company;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/register/company")
public class CompanyRegistration {

    private final CompanyService companyService;

    public CompanyRegistration(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> register(@RequestBody Company company) {
        Company savedCompany = companyService.addCompany(company);
        return ResponseEntity.ok(savedCompany);
    }
}
