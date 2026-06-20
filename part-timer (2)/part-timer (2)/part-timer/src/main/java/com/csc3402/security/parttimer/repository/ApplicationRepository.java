package com.csc3402.security.parttimer.repository;

import com.csc3402.security.parttimer.model.Application;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Existing methods
    List<Application> findByUserUserId(Long userId);
    List<Application> findByJobJobId(Long jobId);
    List<Application> findByUser(User user);
    boolean existsByUserAndJob(User user, Job job);

    @Query("SELECT a FROM Application a WHERE a.user = :user ORDER BY a.applicationDate DESC")
    List<Application> findTop3ByUserOrderByApplicationDateDesc(@Param("user") User user, Pageable pageable);

    // NEW: To retrieve applications by job object
    List<Application> findByJob(Job job);

    // NEW: To retrieve applications for all jobs under a specific company
    List<Application> findByJob_Company(Company company);
}
