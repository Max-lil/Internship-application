package com.example.internshipapplication.JobAd;

import com.example.internshipapplication.Company.Company;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_ads")
public class JobAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Titel på annons, t.ex java-utvecklare
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Vilken tidsperiod annonsen gäller
    private String period;

    private String location;

    // Datum sätts när annons skapas
    private LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
