package com.csc3402.security.parttimer.service;


import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.Application;
import java.util.List;


public interface UserService {
    List<Job> browseJobs();
    void applyToJob(Long jobId, Long userId);
    List<Application> viewApplications(Long userId);


}

