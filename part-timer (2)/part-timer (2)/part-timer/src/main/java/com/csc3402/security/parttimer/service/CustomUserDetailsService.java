package com.csc3402.security.parttimer.service;


import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import com.csc3402.security.parttimer.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    public CustomUserDetailsService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
            );
        }


        Company company = companyRepository.findByEmail(email);
        if (company != null) {
            return new org.springframework.security.core.userdetails.User(
                    company.getEmail(),
                    company.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_COMPANY"))
            );
        }


        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}



