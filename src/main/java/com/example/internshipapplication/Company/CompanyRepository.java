package com.example.internshipapplication.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List <Company> findByNameContainingIgnoreCase(String name);

    List <Company> findByLocationContainingIgnoreCase(String location);

    List <Company> findByIndustryContainingIgnoreCase(String industry);

    List <Company> findByPeriodIgnoreCase(String period);

}
