package com.csc3402.security.parttimer.controller;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import com.csc3402.security.parttimer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class LoginController {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public LoginController(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String redirectByRole(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        var roles = auth.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .toList();
        if (roles.contains("ROLE_ADMIN")) {
            return "admin";
        } else if (roles.contains("ROLE_USER")) {
            return "user";
        } else if (roles.contains("ROLE_COMPANY")) {
            return "company";
        }
        return "login";
    }
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "error", required = false) String error,
                                Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role
    ) {
        if ("USER".equalsIgnoreCase(role)) {
            User user = new User();
            user.setName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("USER");
            userRepository.save(user);
        } else if ("COMPANY".equalsIgnoreCase(role)) {
            Company company = new Company();
            company.setName(username);
            company.setEmail(email);
            company.setPassword(passwordEncoder.encode(password));
            company.setLocation(""); // or let them fill it later
            companyRepository.save(company);
        }
        return "redirect:/login";
    }

}



