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

    @PostMapping
    public String postNotes(@RequestParam("noteId") Integer noteId,
                            @RequestParam("noteTitle") String noteTitle,
                            @RequestParam("noteDescription") String noteDescription,
                            Authentication authentication, Model model){

        User user = userService.getUser(authentication.getName());
        if(noteId != null){
            noteService.updateNote(noteId, noteTitle, noteDescription, user.getUserId());
            log.info(" in NoteController -> postNotes -> user : {} updated => noteTitle: {} , noteDescription: {} " ,authentication.getName(), noteTitle, noteDescription);
            model.addAttribute("successMsg", true);
            return "result";

        }
        log.info(" in NoteController -> postNotes -> user : {} inserted => noteTitle: {} , noteDescription: {} " ,authentication.getName(), noteTitle, noteDescription);

        String error = null;

        int rowsAdded = noteService.createNote(noteTitle, noteDescription, user.getUserId());
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

    @GetMapping("/delete")
    public String deleteNote(@Param("noteId") Integer noteId, Model model){
        noteService.deleteNote(noteId);

        model.addAttribute("successMsg", true);

        return "result";
    }

    @ModelAttribute("allNotes")
    public List<Note> getAllNotes(Authentication authentication){
        User user = userService.getUser(authentication.getName());
        log.info(" NoteController -> getAllNotes( {} ) ", user.getUserId());
        return noteService.getAllNotes(user.getUserId());
    }

}
