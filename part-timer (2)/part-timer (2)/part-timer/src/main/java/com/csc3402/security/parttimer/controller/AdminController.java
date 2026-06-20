package com.csc3402.security.parttimer.controller;


import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.repository.UserRepository;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import com.csc3402.security.parttimer.repository.JobRepository;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.text.AttributedString;
import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;




    @GetMapping
    public String adminPage(Model model) {
        long userCount = userRepository.count();
        long companyCount = companyRepository.count();
        long jobCount = jobRepository.count();


        System.out.println("User Count: " + userCount); // Debug print


        model.addAttribute("userCount", userCount);
        model.addAttribute("companyCount", companyCount);
        model.addAttribute("jobCount", jobCount);


        return "admin"; // this should match src/main/resources/templates/admin.html
    }








    // ✅ Show list of companies
    @GetMapping("/company_list_admin")
    public String companyListAdminPage(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "company_list_admin";
    }


    // ✅ Show list of users
    @GetMapping("/user_list_admin")
    public String userListAdminPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user_list_admin";
    }


    // ✅ Show list of jobs
    @GetMapping("/job_list_admin")
    public String jobListAdminPage(Model model) {
        List<Job> jobs = jobRepository.findAll();
        model.addAttribute("jobs", jobs);
        return "job_list_admin";
    }


    // ✅ REST API: All users
    @ResponseBody
    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // ✅ REST API: All companies
    @ResponseBody
    @GetMapping("/api/companies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    // ✅ REST API: All jobs
    @ResponseBody
    @GetMapping("/api/jobs")
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    // ✅ REST API: User by ID
    @ResponseBody
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ✅ REST API: Company by ID
    @ResponseBody
    @GetMapping("/api/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ✅ REST API: Job by ID
    @ResponseBody
    @GetMapping("/api/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }






    //    -----------------------JOB-------------------
    // ✅ Delete a job
    @DeleteMapping("/api/jobs/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> {
                    jobRepository.delete(job);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }




//    -------------USER-----------------




    // ✅ Delete a user
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user_list_admin";
    }


    // ✅ Update user info (from form modal)
    @PostMapping("/update-user")
    public String updateUser(@RequestParam Long userId,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phoneNo,
                             @RequestParam String role) {


        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(name);
            user.setEmail(email);
            user.setPhoneNo(phoneNo);
            user.setRole(role);
            userRepository.save(user);
        }


        return "redirect:/admin/user_list_admin";
    }


    @PostMapping("/add-user")
    public String addUser(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String phoneNo,
                          @RequestParam String password,
                          @RequestParam String role) {


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNo(phoneNo);
        user.setPassword(passwordEncoder.encode(password)); // ✔️ Correct
        user.setRole(role);


        userRepository.save(user);
        return "redirect:/admin/user_list_admin"; // ✔️ Fix view name if needed
    }


    //    ------------------------COMPANIES-----------------
    @GetMapping("/delete-company/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "redirect:/admin/company_list_admin";
    }




}

