package com.example.internshipapplication.Company;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
