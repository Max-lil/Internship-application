package com.example.internshipapplication.Company;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
