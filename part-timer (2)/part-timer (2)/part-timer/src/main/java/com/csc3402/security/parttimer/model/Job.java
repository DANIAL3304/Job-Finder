package com.csc3402.security.parttimer.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @NotBlank(message = "Job name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    private String jobType;

    private String location;

    private String time;

    private Double salary;

    private LocalDate date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonManagedReference
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<Application> applications = new HashSet<>();

    public Job() {
    }

    public Job(String name, String description, String jobType, String location, String time, Double salary, LocalDate date, Company company) {
        this.name = name;
        this.description = description;
        this.jobType = jobType;
        this.location = location;
        this.time = time;
        this.salary = salary;
        this.date = date;
        this.company = company;
    }

    // Getters and Setters
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salary=" + salary +
                '}';
    }
}
