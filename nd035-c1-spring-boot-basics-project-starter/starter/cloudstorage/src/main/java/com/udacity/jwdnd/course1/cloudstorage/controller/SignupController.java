package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {

        String error = null ;
        if(!userService.isUsernameAvailable(user.getUsername())){
            error = "Username already exist.";
        }

        if(error == null){
            int rowsAdded = userService.createUser(user);

            if (rowsAdded > 0) {
                model.addAttribute("signupSuccess", true);
            }

        } else {
            error = "There was an error during your registration. Please try again.";
            model.addAttribute("signupError", error);
        }
        return "signup";
    }
}
