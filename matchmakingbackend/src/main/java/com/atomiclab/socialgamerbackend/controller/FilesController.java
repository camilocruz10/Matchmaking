package com.atomiclab.socialgamerbackend.controller;

import java.io.IOException;

import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/play")
public class FilesController {
    @Autowired
    FirebaseStorage storageService;

    @PostMapping("/upload")
    public String[] register(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return storageService.uploadFile(multipartFile);
    }
}

