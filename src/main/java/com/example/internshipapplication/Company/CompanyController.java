package com.example.internshipapplication.Company;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/name/{name}")
    public List <Company> getCompanyByName(@PathVariable String name) {
        return companyService.getByName(name);
    }
    @GetMapping("/location/{location}")
    public List <Company> getCompanyByLocation(@PathVariable String location) {
        return companyService.getByLocation(location);
    }
    @GetMapping("/industry/{industry}")
    public List <Company> getCompanyByIndustry(@PathVariable String industry) {
        return companyService.getByIndustry(industry);
    }

    @GetMapping("/lia/{period}")
    public List<Company> getCompaniesByPeriod(@PathVariable String period) {
        return companyService.findByPeriodIgnoreCase(period);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> removeCompanyById(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build(); // 204 = bekräftelse att det är borta
    }



}
