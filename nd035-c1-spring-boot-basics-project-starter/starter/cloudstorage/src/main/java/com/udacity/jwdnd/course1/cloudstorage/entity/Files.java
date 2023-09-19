package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;
}
