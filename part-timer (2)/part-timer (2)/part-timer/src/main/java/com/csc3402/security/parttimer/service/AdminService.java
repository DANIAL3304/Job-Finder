package com.csc3402.security.parttimer.service;

import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.model.Company;
import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<Company> getAllCompanies();
    List<Job> getAllJobs();
    void deleteUser(Long userId);
    void deleteCompany(Long companyId);
    void deleteJob(Long jobId);
}
