package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Blob;

@Data
@AllArgsConstructor
public class Files {
    private Integer fileId;
    private String filename;
    private String contentType;
    private String filesize;
    private Integer userId;
    private byte[] fileData;
}
