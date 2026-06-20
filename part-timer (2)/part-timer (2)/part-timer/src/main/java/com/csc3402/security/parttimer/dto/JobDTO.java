package com.csc3402.security.parttimer.dto;

import java.time.LocalDate;

public class JobDTO {
    private Long jobId;
    private String name;
    private String description;
    private String jobType;
    private String location;
    private String time;
    private Double salary;
    private LocalDate date;
    private Long companyId;

    public JobDTO() {}

    public JobDTO(Long jobId, String name, String description, String jobType, String location, String time, Double salary, LocalDate date, Long companyId) {
        this.jobId = jobId;
        this.name = name;
        this.description = description;
        this.jobType = jobType;
        this.location = location;
        this.time = time;
        this.salary = salary;
        this.date = date;
        this.companyId = companyId;
    }

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
