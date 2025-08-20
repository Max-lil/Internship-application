package com.example.internshipapplication.JobAd;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobAdRepository extends JpaRepository<JobAd, Long> {
    // Hämta alla annonser ett företag lagt ut
    List<JobAd> findByCompanyId(Long companyId);
}
