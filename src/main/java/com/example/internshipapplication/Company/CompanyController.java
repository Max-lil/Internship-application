package com.example.internshipapplication.Company;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")//Tillåter typ åtkomst från react-porten
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

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> removeCompanyById(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build(); // 204 = bekräftelse att det är borta
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if(company != null) {
            return ResponseEntity.ok(company.getEmail());
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(org.springframework.security.core.Authentication auth) {
        return companyService.findByEmail(auth.getName())
                .map(c -> ResponseEntity.ok(java.util.Map.of(
                        "id", c.getId(),
                        "name", c.getName(),
                        "email", c.getEmail()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

}
