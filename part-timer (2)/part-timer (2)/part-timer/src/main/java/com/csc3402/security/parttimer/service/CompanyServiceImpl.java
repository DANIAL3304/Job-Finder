package com.csc3402.security.parttimer.service.impl;

import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import com.csc3402.security.parttimer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    @Override
    public void postJob(Job job, Long companyId) {

    }

    @Override
    public void updateJob(Job job) {

    }

    @Override
    public void deleteJob(Long jobId) {

    }

    @Override
    public List<Job> getCompanyJobs(Long companyId) {
        return List.of();
    }

    // Add other methods from interface if needed
}
