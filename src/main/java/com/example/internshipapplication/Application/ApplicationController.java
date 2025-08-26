package com.example.internshipapplication.Application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {this.applicationService = applicationService;}

    /*@PostMapping("/create")
    public ResponseEntity<?> createNewApplication(
            @RequestParam Long studentId,
            @RequestParam Long jobAdId,
            @RequestParam(required = false) String message) {
        try {
            Application saved = applicationService.createNewApplication(studentId, jobAdId, message);
            return ResponseEntity.ok("Ansökan skickad!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }*/

    @GetMapping("/company/{companyId}")
    public List<Application> getApplicationForCompany(@PathVariable Long companyId) {
        return applicationService.getApplicationForCompany(companyId);
    }


    // För inloggad student
    @PostMapping("/apply/{jobAdId}")
    public ResponseEntity<Application> applyForJobAd(
            @PathVariable Long jobAdId,
            @RequestBody Application application,
            Principal principal)
    {
        Application saved = applicationService.createApplicationForJobAd(
                principal.getName(),
                jobAdId,
                application.getMessage()
        );
        return ResponseEntity.ok(saved);
    }

}
