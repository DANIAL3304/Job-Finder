package com.csc3402.security.parttimer.service;

import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;

import java.util.List;

public interface CompanyService {
    Company findByEmail(String email);
    void postJob(Job job, Long companyId);
    void updateJob(Job job);
    void deleteJob(Long jobId);
    List<Job> getCompanyJobs(Long companyId);
}
