package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
@Slf4j
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @PostMapping
    public String postNotes(Authentication authentication, Note note, Model model){

        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        log.info(" in NoteController -> postNotes -> {} inserted {}" ,authentication.getName(), note);

//        noteService.createNote(noteObject);

//        model.addAttribute("", noteService.createNote(note));

        String error = null;

        int rowsAdded = noteService.createNote(note);
        if(rowsAdded < 0){
            error = "There was an error during your note creation. Please try again.";
        }

        if(error == null){
            model.addAttribute("successMsg", true);
        } else  {
            model.addAttribute("errorMsg", error);
        }
        return "result";
    }
}
