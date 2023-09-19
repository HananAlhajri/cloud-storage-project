package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
@Slf4j
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    @GetMapping
    public String getHomePage(Authentication authentication, Model model){
        User user = userService.getUser(authentication.getName());
        log.info(" HomeController -> getHomePage() for user with Id {} ", user.getUserId() );
        log.info( " all notes for {} ===> {}", user.getUserId(), noteService.getAllNotes(user.getUserId()) );
        model.addAttribute("allNotes", noteService.getAllNotes(user.getUserId()));
        return "home";
    }

}
