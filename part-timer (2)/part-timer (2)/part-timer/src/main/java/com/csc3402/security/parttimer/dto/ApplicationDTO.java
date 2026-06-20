package com.csc3402.security.parttimer.dto;

import java.time.LocalDate;

public class ApplicationDTO {
    private Long applicationId;
    private LocalDate applicationDate;
    private String status;
    private Long userId;
    private Long jobId;

    public ApplicationDTO() {}

    public ApplicationDTO(Long applicationId, LocalDate applicationDate, String status, Long userId, Long jobId) {
        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
        this.status = status;
        this.userId = userId;
        this.jobId = jobId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
