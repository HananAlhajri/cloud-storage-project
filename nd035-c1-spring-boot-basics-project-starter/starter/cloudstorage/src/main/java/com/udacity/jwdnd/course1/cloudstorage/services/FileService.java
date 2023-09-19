package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;


    public void uploadFile(MultipartFile multipartFile, Integer userId) throws Exception {
        Files newFile = new Files(
                null,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize(),
                userId,
                multipartFile.getBytes()
                );

        try {
            fileMapper.save(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Files> getAllFiles(Integer userId) {
        return fileMapper.findFilesByUserId(userId);
    }

    public Files getFileById(Integer fileId) {
        return fileMapper.findById(fileId);
    }

    public void deleteFile(Integer fileId) throws IOException {
        try {
            fileMapper.deleteById(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFilenameAvailable(MultipartFile multipartFile, Integer userId) {
        Boolean isFilenameAvailable = true;
        List<Files> files = fileMapper.findFilesByUserId(userId);
        for (int i = 0; i < files.size(); i++){
            Files currFile = files.get(i);
            if (currFile.getFileName().equals(multipartFile.getOriginalFilename())) {
                isFilenameAvailable = false;
                break;
            }
        }
        return isFilenameAvailable;
    }
}
