package com.csc3402.security.parttimer.repository;



import com.csc3402.security.parttimer.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByEmail(String email);
}
