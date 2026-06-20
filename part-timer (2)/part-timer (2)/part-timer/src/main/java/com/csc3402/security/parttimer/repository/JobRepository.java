package com.csc3402.security.parttimer.repository;

import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Find all jobs by job type (e.g., Full-Time, Part-Time)
    List<Job> findByJobType(String jobType);

    // Find all jobs by company ID
    List<Job> findByCompany_CompanyId(Long companyId);

    // Optional: Find jobs by keyword in job name
    List<Job> findByNameContainingIgnoreCase(String keyword);

    List<Job> findByCompany(Company company);

    long countByCompany(Company company);

}
