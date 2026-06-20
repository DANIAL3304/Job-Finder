package com.csc3402.security.parttimer.controller;


import com.csc3402.security.parttimer.model.Application;
import com.csc3402.security.parttimer.model.Job;
import com.csc3402.security.parttimer.model.User;
import com.csc3402.security.parttimer.repository.ApplicationRepository;
import com.csc3402.security.parttimer.repository.JobRepository;
import com.csc3402.security.parttimer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;




import java.security.Principal;
import java.time.LocalDate;
import java.util.List;




@Controller


@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;


    @Autowired
    private ApplicationRepository applicationRepository; // ✅ Add this






    // ✅ View: Render user page
    @GetMapping
    public String showUserPage(Model model, Principal principal) {
        String email = principal.getName();


        // Only call once
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        // Fetch top 3 recent applications using pagination
        Pageable topThree = PageRequest.of(0, 3);
        List<Application> recentApplications = applicationRepository.findTop3ByUserOrderByApplicationDateDesc(user, topThree);




        model.addAttribute("user", user);                 // full user object
        model.addAttribute("name", user.getName());       // just the name
        model.addAttribute("applications", recentApplications);


        return "user"; // HTML template: user.html
    }








    // ✅ API: Get all users (JSON)
    @GetMapping("/api")
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // ✅ API: Get user by ID
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable("id") Long applicationId, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));


        // ✅ Only allow delete if the logged-in user owns the application
        if (app.getUser().getUserId().equals(user.getUserId())) {
            applicationRepository.delete(app);
        }


        return "redirect:/user?deleted"; // You can display a message if you want
    }


    @PostMapping("/applications/delete-all/{id}")
    public String deleteFromMyApplications(@PathVariable("id") Long applicationId, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));


        if (app.getUser().getUserId().equals(user.getUserId())) {
            applicationRepository.delete(app);
        }


        return "redirect:/user/my-applications?deleted";  // Stay on myApplications page
    }






    // ✅ API: Register a user
    @PostMapping("/api/register")
    @ResponseBody
    public User registerUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    // ✅ API: Login a user
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String role = user.getRole().toUpperCase();
            String redirectPath = switch (role) {
                case "ADMIN" -> "/admin";
                case "USER" -> "/user";
                case "COMPANY" -> "/company";
                default -> null;
            };
            if (redirectPath != null) {
                return ResponseEntity.ok().body("{\"redirect\": \"" + redirectPath + "\"}");
            }
        }
        return ResponseEntity.badRequest().body("{\"error\": \"Invalid email or password\"}");
    }


    // ✅ API: Update user
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setPhoneNo(updatedUser.getPhoneNo());
                    user.setRole(updatedUser.getRole());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<Application> applications = applicationRepository.findByUser(user); // ✅


        model.addAttribute("user", user);
        model.addAttribute("applications", applications); // ✅ required for ${app}


        return "user_profile";
    }




    @PostMapping("/profile/update")
    public String updateUserProfile(@ModelAttribute("user") User updatedUser, Principal principal) {
        String email = principal.getName();
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        existingUser.setName(updatedUser.getName());
        existingUser.setPhoneNo(updatedUser.getPhoneNo());


        // Update password only if a new one is provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
        }


        userRepository.save(existingUser);
        return "redirect:/user/profile?success";
    }
    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }


    @GetMapping("/my-applications")
    public String showAllApplications(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<Application> allApplications = applicationRepository.findByUser(user);
        model.addAttribute("applications", allApplications);
        return "myApplications"; // corresponds to myApplications.html
    }






}

