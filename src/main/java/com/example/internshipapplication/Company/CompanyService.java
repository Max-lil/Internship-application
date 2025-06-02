package com.example.internshipapplication.Company;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
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
        if (companyRepository.findByLocationContainingIgnoreCase(location) != null) {
            return companyRepository.findByLocationContainingIgnoreCase(location);
        }
        return null;
    }
}
