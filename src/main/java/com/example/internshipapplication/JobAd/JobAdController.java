package com.example.internshipapplication.JobAd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/jobads")
public class JobAdController {

    private final JobAdService jobAdService;

    public JobAdController(JobAdService jobAdService) {
        this.jobAdService = jobAdService;
    }

    // Skapa annons åt företag
    @PostMapping("/create/{companyId}")
    public ResponseEntity<JobAd> createJobAd(
            @PathVariable Long companyId,
            @RequestBody JobAd jobAd) {
        JobAd saved = jobAdService.createJobAd(companyId, jobAd);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<JobAd> getAll(){
        return jobAdService.getAllJobAds();
    }

    // Hämta ett företags annonser
    @GetMapping("/company/{companyId}")
    public List<JobAd> getByCompany(@PathVariable Long companyId) {
        return jobAdService.getJobAdsByCompany(companyId);
    }

    // Hämta en specifik annons
    @GetMapping("/{id}")
    public ResponseEntity<JobAd> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobAdService.getJobAdById(id));
    }

}
