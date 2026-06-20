package com.csc3402.security.parttimer.controller;


import com.csc3402.security.parttimer.model.Application;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.model.Company;
import com.csc3402.security.parttimer.repository.ApplicationRepository;
import com.csc3402.security.parttimer.repository.JobRepository;
import com.csc3402.security.parttimer.repository.UserRepository;
import com.csc3402.security.parttimer.repository.CompanyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class JobController {


    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private UserRepository userRepository;




    @Autowired
    private ApplicationRepository applicationRepository;


    @GetMapping("/job_list_user")
    public String showJobListForUser(Model model, Principal principal, HttpServletRequest request) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<Job> allJobs = jobRepository.findAll();
        List<Application> applications = applicationRepository.findByUser(user);


        Set<Long> appliedJobIds = applications.stream()
                .map(app -> app.getJob().getJobId())
                .collect(Collectors.toSet());


        model.addAttribute("jobs", allJobs);
        model.addAttribute("appliedJobIds", appliedJobIds);


        // ✅ Add CSRF token to model manually
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", token);


        return "job_list_user";
    }




    @PostMapping("/update-job")
    public String updateJob(@ModelAttribute Job formJob, RedirectAttributes redirectAttributes) {
        Optional<Job> optionalJob = jobRepository.findById(formJob.getJobId());
        if (optionalJob.isPresent()) {
            Job existingJob = optionalJob.get();


            // Update editable fields
            existingJob.setName(formJob.getName());
            existingJob.setJobType(formJob.getJobType());
            existingJob.setLocation(formJob.getLocation());
            existingJob.setSalary(formJob.getSalary());
            existingJob.setDate(formJob.getDate());


            jobRepository.save(existingJob);


            // ✅ Set flash attribute for success
            redirectAttributes.addFlashAttribute("jobUpdateSuccess", true);
        }


        return "redirect:/company";
    }








    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        // Assuming you fetch companyId from principal or session
        List<Job> jobs = jobRepository.findAll(); // or findByCompanyId(...)
        model.addAttribute("jobs", jobs);
        return "company_dashboard"; // your HTML page
    }




    // ✅ Allow a user to apply for a job
    @PostMapping("/job/apply/{jobId}")
    public String applyToJob(@PathVariable Long jobId,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job ID"));


        boolean alreadyApplied = applicationRepository.existsByUserAndJob(user, job);
        if (alreadyApplied) {
            redirectAttributes.addFlashAttribute("error", "You have already applied for this job.");
            return "redirect:/job_list_user";
        }


        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus("Pending");
        application.setApplicationDate(LocalDate.now()); // ✅ Make sure this is set


        applicationRepository.save(application); // ✅ This should insert data


        redirectAttributes.addFlashAttribute("success", "Job application submitted!");


        return "redirect:/job_list_user";
    }




    @GetMapping("/jobs")
    public String getJobList(Model model) {
        // Optional: add model attributes here
        return "job_list_user";
    }
}















