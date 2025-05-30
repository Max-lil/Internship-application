package com.example.internshipapplication.Company;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Company";
    }


}
