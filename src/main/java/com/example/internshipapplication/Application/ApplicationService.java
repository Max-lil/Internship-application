package com.example.internshipapplication.Application;

import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.Company.CompanyRepository;
import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ApplicationService {

    private ApplicationRepository applicationRepository;

    private CompanyRepository companyRepository;

    private StudentRepository studentRepository;

    public ApplicationService(ApplicationRepository applicationRepository, CompanyRepository companyRepository, StudentRepository studentRepository) {
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
    }

    public Application createNewApplication(Application application) {
        Long studentId = application.getStudent().getId();
        Long companyId = application.getCompany().getId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student med ID " + studentId + " finns inte."));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Företag med ID " + companyId + " finns inte."));

        application.setStudent(student);
        application.setCompany(company);

        return applicationRepository.save(application);
    }

    public List<Application> getApplicationForCompany(Long companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }
}
