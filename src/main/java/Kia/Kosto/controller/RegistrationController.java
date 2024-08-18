package Kia.Kosto.controller;

import Kia.Kosto.model.User;
import Kia.Kosto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/register")
    public String showLoginForm() {
        return "Registration";
    }

    @PostMapping("/registerData")
    public String register(@RequestParam String userName,
                           @RequestParam String password, Model model) {
        // check userName and Password
        if (userRepository.existsByUserName(userName)) {
            model.addAttribute("errorMessage", " Sorry, There is exist of this Username and Password");
            return "Registration";
        }


        User user = new User(userName,password);
        userRepository.save(user);
        return "login"; // Redirect to login page after successful registration
    }



}
