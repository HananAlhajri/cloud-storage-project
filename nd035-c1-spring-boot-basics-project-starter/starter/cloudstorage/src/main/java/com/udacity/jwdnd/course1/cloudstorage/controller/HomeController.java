package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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

    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    @GetMapping
    public String getHomePage(Authentication authentication, Model model){
        User user = userService.getUser(authentication.getName());
        log.info(" HomeController -> getHomePage() for user with Id {} ", user.getUserId() );
        log.info( " all notes for {} ===> {}", user.getUserId(), noteService.getAllNotes(user.getUserId()) );
        model.addAttribute("allFiles", fileService.getAllFiles(user.getUserId()));
        model.addAttribute("allNotes", noteService.getAllNotes(user.getUserId()));
        model.addAttribute("allCredentials", credentialService.getAllCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

}
