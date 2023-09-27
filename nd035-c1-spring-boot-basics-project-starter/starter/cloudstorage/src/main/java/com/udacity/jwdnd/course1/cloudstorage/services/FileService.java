package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;


    public void uploadFile(MultipartFile file, Integer userId) throws IOException {
        try {
            Files newFile = new Files(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getBytes(), userId);
            fileMapper.uploadFile(newFile);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public List<Files> getAllFiles(Integer userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    public Files getFileById(Integer fileId) {
        return fileMapper.findById(fileId);
    }

    public void deleteFile(Integer fileId) {
            fileMapper.deleteById(fileId);
    }

    public boolean isFilenameExist(String filename, Integer userId) {
        List<Files> files = fileMapper.findFilesByUserId(userId);
        boolean exist = false;
        for (Files file : files) {
            if (file.getFileName().equals(filename)) {
                exist = true;
            break;
            }
        }
        return exist;
    }
}
