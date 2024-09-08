package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.inMemoryUserDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @GetMapping("/welcome")
    public String showContinuePage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "welcome";
    }

    @PostMapping("/register")
    public String registerUser(String username, String password, Model model) {
        if (inMemoryUserDetailsManager.userExists(username)) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }

        inMemoryUserDetailsManager.createUser(
                User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .roles("USER")
                        .build()
        );

        return "redirect:/login";
    }
}