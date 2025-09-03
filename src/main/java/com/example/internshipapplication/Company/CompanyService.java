package com.example.internshipapplication.Company;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public List <Company> getByName(String name) {
            if(name != null){
                return companyRepository.findByNameContainingIgnoreCase(name);
            }
            return null;

    }

    public List <Company> getByLocation(String location) {
        if (location != null) {
            return companyRepository.findByLocationContainingIgnoreCase(location);
        }
        return null;
    }
    public List <Company> getByIndustry(String industry) {
        if (industry != null) {
            return companyRepository.findByIndustryContainingIgnoreCase(industry);
        }
        return null;
    }


    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id).get();
        companyRepository.delete(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).get();
    }

    public Optional<Company> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

}

