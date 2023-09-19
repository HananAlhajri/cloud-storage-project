package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication auth,
                             Model model) throws IOException {
        String fileUploadError = null;
        User user = this.userService.getUser(auth.getName());
        if (fileService.isFilenameAvailable(fileUpload, user.getUserId())) {
            try {
                fileService.uploadFile(fileUpload, user.getUserId());
                model.addAttribute("fileUploadSuccess", "File successfully uploaded.");
            } catch (Exception e) {
                fileUploadError = e.toString();
                model.addAttribute("fileError", fileUploadError);
            }
        } else {
            model.addAttribute("fileError", "Can't upload files with duplicate names.");
        }

        model.addAttribute("files", this.fileService.getAllFiles(user.getUserId()));
        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserId()));
        return "home";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId,
                                       Authentication auth,
                                       Model model) throws IOException {
        Files file = this.fileService.getFileById(fileId);
        String contentType = file.getContentType();
        String fileName = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.getFileData());
    }

    @RequestMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId,
                             Authentication auth,
                             Model model) throws IOException {
        String fileDeleteError = null;
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("fileDeleteSuccess", "File successfully deleted.");
        } catch (Exception e) {
            fileDeleteError = e.toString();
            model.addAttribute("fileError", fileDeleteError);
        }
        User user = this.userService.getUser(auth.getName());
        model.addAttribute("files", this.fileService.getAllFiles(user.getUserId()));
        model.addAttribute("notes", this.noteService.getAllNotes(user.getUserId()));
//        model.addAttribute("credentials", this.credentialService.getAllCredentials(user.getUserid()));
        return "home";
    }

}
