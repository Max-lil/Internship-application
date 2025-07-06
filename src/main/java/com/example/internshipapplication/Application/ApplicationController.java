package com.example.internshipapplication.Application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {this.applicationService = applicationService;}

    @PostMapping("/create")
    public ResponseEntity<?> createNewApplication(@RequestBody Application application) {
        try {
            Application saved = applicationService.createNewApplication(application);
            return ResponseEntity.ok("Sparandet lyckades!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/company/{companyId}")
    public List<Application> getApplicationForCompany(@PathVariable Long companyId) {
        return applicationService.getApplicationForCompany(companyId);
    }
}
