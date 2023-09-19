package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
@Slf4j
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

//    @GetMapping
//    public String getNotes(Model model){
//        model.addAttribute("allNotes", noteService.getAllNotes());
//        return "home";
//    }

    @PostMapping
    public String postNotes(Authentication authentication, Note note, Model model){

        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        log.info(" in NoteController -> postNotes -> {} inserted {}" ,authentication.getName(), note);

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

    @ModelAttribute("allNotes")
    public List<Note> getAllNotes(Authentication authentication){
        User user = userService.getUser(authentication.getName());
        log.info(" NoteController -> getAllNotes( {} ) ", user.getUserId());
        return noteService.getAllNotes(user.getUserId());
    }

    @GetMapping("/delete")
    public String deleteNote(@Param("noteId") Integer noteId, Model model){
        noteService.deleteNote(noteId);

        model.addAttribute("successMsg", true);

        return "result";

    }

}
