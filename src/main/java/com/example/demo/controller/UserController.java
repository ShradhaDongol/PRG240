package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        userService.addUser(new User(username, email, password));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        boolean isAuthenticated = userService.authenticateUser(email, password);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "login";
    }
}