package com.csc3402.security.parttimer.service;

import com.csc3402.security.parttimer.model.Application;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Find all applications related to a specific company.
     */
    public List<Application> findAllByCompany(Company company) {
        return applicationRepository.findByJob_Company(company);
    }

    /**
     * Find all applications for a specific job.
     */
    public List<Application> findByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    /**
     * Find application by ID.
     */
    public Application findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    /**
     * Update the status of an application (e.g., Accepted or Rejected).
     */
    public void updateStatus(Long id, String status) {
        Application application = findById(id);
        application.setStatus(status);
        applicationRepository.save(application);
    }

    /**
     * Save a new application.
     */
    public void save(Application application) {
        applicationRepository.save(application);
    }

    /**
     * Delete application by ID.
     */
    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }
}
