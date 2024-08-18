package Kia.Kosto.controller;

import Kia.Kosto.model.User;
import Kia.Kosto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashMap;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // For demonstration purposes, let's just check if the username and password are "admin"
        if (userRepository.existsByUserName(username)) {
            //  save username and password in txt file
            new User().saveToTextFile(username, password);
            return "HomePage";
        } else {
            System.out.println(username+" "+password);
            Map<String, Object> additionalFields = new HashMap<>();
            additionalFields.put("email", "user@example.com"); // Example additional field
            additionalFields.put("age", 25); // Another example additional field

            // If login fails, you can add an error message to be displayed on the login page
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }

}