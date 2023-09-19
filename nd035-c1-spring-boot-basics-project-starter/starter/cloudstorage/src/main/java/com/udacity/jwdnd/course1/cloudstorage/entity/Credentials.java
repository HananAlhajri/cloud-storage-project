package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Credentials {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private Integer userId;
    private String key;

    public Credentials(String url, String username, String password, Integer userId, String key) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }
}
