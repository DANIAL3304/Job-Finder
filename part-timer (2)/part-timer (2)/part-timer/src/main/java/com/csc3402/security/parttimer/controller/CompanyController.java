package com.csc3402.security.parttimer.controller;


import com.csc3402.security.parttimer.model.Application;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import com.csc3402.security.parttimer.repository.JobRepository;
import com.csc3402.security.parttimer.service.ApplicationService;
import com.csc3402.security.parttimer.service.CompanyService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private ApplicationService applicationService;


    @Autowired
    private CompanyService companyService;


    // ✅ Show dashboard with logged-in company
    @GetMapping
    public String showCompanyDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<Company> companyOpt = Optional.ofNullable(companyRepository.findByEmail(email));


        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            List<Job> jobs = jobRepository.findByCompany(company);
            long jobCount = jobRepository.countByCompany(company);

            model.addAttribute("companies", List.of(company));
            model.addAttribute("companyId", company.getCompanyId());
            model.addAttribute("jobs", jobs);
            model.addAttribute("jobCount", jobCount);

            return "company";
        } else {
            return "redirect:/login?error";
        }
    }


    // ✅ Show job post form
    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "post-job";
    }


    // ✅ Save job post
    @PostMapping("/post-job")
    public String saveJob(@ModelAttribute Job job, Principal principal) {
        Company company = companyRepository.findByEmail(principal.getName());
        job.setCompany(company);
        job.setDate(LocalDate.now());
        jobRepository.save(job);
        return "redirect:/company";
    }


    // ✅ View job applications
    @GetMapping("/manage-applications")
    public String viewApplications(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Company company = companyService.findByEmail(userDetails.getUsername());
        List<Application> applications = applicationService.findAllByCompany(company);
        model.addAttribute("applications", applications);
        return "manage_applications";
    }


    // ✅ Update application status
    @PostMapping("/application/{id}/update")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        applicationService.updateStatus(id, status);
        return "redirect:/company/manage-applications";
    }


    // ✅ View specific company profile
    @GetMapping("/profile/{id}")
    public String viewCompanyProfile(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        model.addAttribute("company", company);
        return "company_profile";
    }


    // ✅ Save or update company profile
    @PostMapping("/profile/save")
    public String saveOrUpdateCompany(@ModelAttribute("company") Company company) {
        if (company.getCompanyId() != null) {
            Company existingCompany = companyRepository.findById(company.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Company not found"));
            existingCompany.setName(company.getName());
            existingCompany.setEmail(company.getEmail());
            existingCompany.setPhone(company.getPhone());
            existingCompany.setLocation(company.getLocation());
            existingCompany.setDescription(company.getDescription());
            existingCompany.setPassword(existingCompany.getPassword()); // preserve
            companyRepository.save(existingCompany);
            return "redirect:/company/profile/" + existingCompany.getCompanyId() + "?success";
        } else {
            return "redirect:/company?error=id-missing";
        }
    }


    @GetMapping("/aboutCompany")
    public String showAboutPage() {
        return "aboutCompany";
    }


    // ✅ REST API - get all companies
    @GetMapping("/api")
    @ResponseBody
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    // ✅ REST API - get one company
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ✅ REST API - create new company
    @PostMapping("/api")
    @ResponseBody
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }


    // ✅ REST API - update existing company
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updated) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(updated.getName());
                    company.setEmail(updated.getEmail());
                    company.setLocation(updated.getLocation());
                    if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
                        company.setPassword(updated.getPassword());
                    }
                    companyRepository.save(company);
                    return ResponseEntity.ok("Company updated successfully!");
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/update-job")
    public String updateJob(@ModelAttribute Job job) {
        System.out.println("Updating job: " + job);
        jobRepository.save(job);  // likely fails if jobId is null
        return "redirect:/company";
    }
    @PostMapping("/delete-job")
    public String deleteJob(@RequestParam Long jobId) {
        jobRepository.deleteById(jobId);
        return "redirect:/company";
    }


}

