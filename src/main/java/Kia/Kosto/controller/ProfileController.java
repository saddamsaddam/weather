package Kia.Kosto.controller;

import Kia.Kosto.model.User;
import Kia.Kosto.repository.UserRepository;
import Kia.Kosto.controller.LoginController.*;
import Kia.Kosto.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping("/profile")
    public String showLoginForm(Model model) {
         User user=new User().retrieveUserData();
        if (user != null) {
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("password", user.getPassword());
        }
        return "Profile";
    }


    @Transactional // Add this annotation to ensure the method runs within a transaction
    @PostMapping("/updateUserData")
    public String updateUserData(@RequestParam String userName,
                                 @RequestParam String password, Model model) {
        if (userRepository.existsByUserNameAndPassword(userName,password)) {
            // check username and password are exist or not
            model.addAttribute("errorMessage", "Sorry,There is exist of this Username and Password");
            return "error";
        }

        // remove previous data
        User userPrevious=new User().retrieveUserData();
        userRepository.deleteByUserNameAndPassword(userPrevious.getUserName(), userPrevious.getPassword());

        // insert data to database
        User user = new User(userName,password);
        new User().saveToTextFile(userName, password);
        userRepository.save(user);

        // update likedCity table also
        weatherRepository.updateUserName(userPrevious.getUserName(),userName);

        return "HomePage";// Redirect to login page after successful registration
    }
}