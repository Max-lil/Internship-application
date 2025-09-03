package com.example.internshipapplication.JobAd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAdRepository extends JpaRepository<JobAd, Long> {
    List<JobAd> findByCompanyId(Long companyId);
}
