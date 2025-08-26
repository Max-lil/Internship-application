package com.example.internshipapplication.JobAd;

import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.Company.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAdService {

    private final JobAdRepository jobAdRepository;
    private final CompanyRepository companyRepository;

    public JobAdService(JobAdRepository jobAdRepository, CompanyRepository companyRepository) {
        this.jobAdRepository = jobAdRepository;
        this.companyRepository = companyRepository;
    }

    // Skapa annons kopplat till företags-ID
    public JobAd createJobAd(Long companyId, JobAd jobAd) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Företag kan ej hittas"));
        jobAd.setCompany(company);
        jobAd.setLocation(company.getLocation());
        return jobAdRepository.save(jobAd);
    }

    public List<JobAd> getAllJobAds() {
        return jobAdRepository.findAll();
    }

    // Hämta ett företags alla annonser
    public List<JobAd> getJobAdsByCompany(Long companyId) {
        return jobAdRepository.findByCompanyId(companyId);
    }

    // Hämta specifik annons
    public JobAd getJobAdById(Long id) {
        return jobAdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kunde ej hitta annonsen"));
    }

    public JobAd createJobAdForLoggedInCompany(String companyEmail, JobAd jobAd) {
        // Hämta företaget baserat på email
        Company company = companyRepository.findByEmail(companyEmail)
                .orElseThrow(() -> new RuntimeException("Företag med email " + companyEmail + " finns inte."));

        // Koppla annonsen till företaget
        jobAd.setCompany(company);

        return jobAdRepository.save(jobAd);
    }
}
