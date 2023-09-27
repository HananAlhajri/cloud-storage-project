package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/credentials")
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    @PostMapping
    public String postCredential(@RequestParam("credentialId") Integer credentialId, @RequestParam("url") String url,
                                      @RequestParam("username") String username, @RequestParam("password") String password,
                                      Authentication authentication, Model model){
        User user = userService.getUser(authentication.getName());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String ENCPassword = encryptionService.encryptValue(password, encodedKey);
        Credentials credential = new Credentials(url, username, ENCPassword, encodedKey, user.getUserId());

        if(credentialId != null ){
            credentialService.updateCredential(credential, credentialId);
            model.addAttribute("successMsg", true);
        } else {
            int rowsAdded = credentialService.createCredential(credential);
            if( rowsAdded > 0 ){
                model.addAttribute("successMsg", true);
            }
            else {
                model.addAttribute("errorMsg", "Sorry, something went wrong :/");
            }
        }
        return "result";
    }

    @RequestMapping("/delete")
    public String deleteCredential(@Param("credentialId") Integer credentialId, Model model) {
        try {
            credentialService.deleteCredential(credentialId);
            model.addAttribute("successMsg", true);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Sorry, something went wrong :/");
        }
        return "result";
    }

}