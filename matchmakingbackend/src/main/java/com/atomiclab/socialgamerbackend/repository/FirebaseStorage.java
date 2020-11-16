package com.atomiclab.socialgamerbackend.repository;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FirebaseStorage {
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException;

    public String downloadFile(String fileName) throws Exception;

    public boolean deleteFile(String fileName);
}
