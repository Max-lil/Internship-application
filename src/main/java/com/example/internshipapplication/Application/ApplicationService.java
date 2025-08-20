package com.example.internshipapplication.Application;

import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.Company.CompanyRepository;
import com.example.internshipapplication.JobAd.JobAd;
import com.example.internshipapplication.JobAd.JobAdRepository;
import com.example.internshipapplication.Student.Student;
import com.example.internshipapplication.Student.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ApplicationService {

    private final JobAdRepository jobAdRepository;
    private ApplicationRepository applicationRepository;

    private CompanyRepository companyRepository;

    private StudentRepository studentRepository;

    public ApplicationService(ApplicationRepository applicationRepository, CompanyRepository companyRepository, StudentRepository studentRepository, JobAdRepository jobAdRepository) {
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
        this.jobAdRepository = jobAdRepository;
    }

    public Application createNewApplication(Long studentId, Long jobAdId, String message) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student ej hittad med ID" + studentId));

        JobAd jobAd = jobAdRepository.findById(jobAdId)
                .orElseThrow(() -> new IllegalArgumentException("Annons ej hittad med ID" + jobAdId));

        Company company = jobAd.getCompany(); // Hämta företaget direkt från annonsen

        Application application = new Application();
        application.setStudent(student);
        application.setCompany(company);
        application.setJobAd(jobAd);
        application.setMessage(message);
        application.setCvFile(student.getCvFile());
        return applicationRepository.save(application);

    }

    public List<Application> getApplicationForCompany(Long companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }
}
