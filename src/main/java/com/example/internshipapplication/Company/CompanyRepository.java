package com.example.internshipapplication.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List <Company> findByNameContainingIgnoreCase(String name);

    List <Company> findByLocationContainingIgnoreCase(String location);

    List <Company> findByIndustryContainingIgnoreCase(String industry);

    Optional<Company> findByEmail(String email);

}
