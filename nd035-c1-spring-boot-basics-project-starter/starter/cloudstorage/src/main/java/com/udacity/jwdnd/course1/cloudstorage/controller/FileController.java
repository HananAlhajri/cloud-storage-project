package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    private final UserService userService;
    public final static long KB  = 1024;
    public final static long MB = 1024 * KB;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        User user = userService.getUser(authentication.getName());
        if (fileUpload.isEmpty() || fileUpload.getSize() < 0 || fileUpload.getName().isEmpty()) {
            model.addAttribute("errorMsg", "Can't upload large files!");
        }
        else if (fileUpload.getSize() > MB) {
            throw new IOException();
        }
        else if (fileService.isFilenameExist(fileUpload.getOriginalFilename(), user.getUserId())) {
            model.addAttribute("errorMsg", "Can't upload files with duplicate names.");
        }
        else {
            fileService.uploadFile(fileUpload, user.getUserId());
            model.addAttribute("successMsg", true);
        }
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId){
        Files file = fileService.getFileById(fileId);
        String contentType = file.getContentType();
        String filename = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file.getFileData());
    }

    @RequestMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model){
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("successMsg", true);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Sorry, couldn't delete your file.");
        }
        return "result";
    }

}
