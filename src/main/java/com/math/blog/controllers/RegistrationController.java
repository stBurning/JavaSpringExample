package com.math.blog.controllers;

import com.math.blog.models.User;
import com.math.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String toRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registrate(@Valid User user, BindingResult bindingResult, Model model) {

            if (userRepository.findByEmail(user.getEmail()) != null) {
                bindingResult
                        .rejectValue("email", "error.user",
                                "There is already a user registered with the email provided");
            }
            if (userRepository.findByUsername(user.getUsername()) != null) {
                bindingResult
                        .rejectValue("username", "error.user",
                                "There is already a user registered with the username provided");
            }

            if (!bindingResult.hasErrors()) {
                // Registration successful, save user

                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);

                model.addAttribute("successMessage", "User has been registered successfully");
                model.addAttribute("user", new User());
                return "/login";
            }
        return "/registration";
        }
}
