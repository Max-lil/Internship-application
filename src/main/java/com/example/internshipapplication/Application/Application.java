package com.example.internshipapplication.Application;

import com.example.internshipapplication.Company.Company;
import com.example.internshipapplication.JobAd.JobAd;
import com.example.internshipapplication.Student.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

    @Entity
    @Table(name = "applications")
    public class Application {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "student_id", nullable = false)
        private Student student;

        @ManyToOne
        @JoinColumn(name = "company_id", nullable = false)
        private Company company;

        private String message;
        private String cvFile;

        @Column(name = "application_date")
        private LocalDateTime applicationDate = LocalDateTime.now();

        @ManyToOne
        @JoinColumn(name = "job_ad_id", nullable = false)
        private JobAd jobAd;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCvFile() {
            return cvFile;
        }

        public void setCvFile(String cvFile) {
            this.cvFile = cvFile;
        }

        public LocalDateTime getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(LocalDateTime applicationDate) {
            this.applicationDate = applicationDate;
        }

        public JobAd getJobAd() {
            return jobAd;
        }
        public void setJobAd(JobAd jobAd) {
            this.jobAd = jobAd;
        }
    }
