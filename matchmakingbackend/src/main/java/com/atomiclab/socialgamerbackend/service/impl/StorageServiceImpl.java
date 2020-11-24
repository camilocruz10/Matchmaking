package com.atomiclab.socialgamerbackend.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.atomiclab.socialgamerbackend.service.StorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;

import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    private static StorageOptions storageOptions;

    @PostConstruct
    private static void initializeStorage() throws IOException {
        String projectId = "spring-course-c4e5a";
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("spring-course-c4e5a-firebase-adminsdk-f0nr3-9a6cff1a6f.json");
        } catch (FileNotFoundException e) {
            serviceAccount = new FileInputStream("WEB-INF/classes/spring-course-c4e5a-firebase-adminsdk-f0nr3-9a6cff1a6f.json");
            e.printStackTrace();
        }
        storageOptions = StorageOptions.newBuilder().setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
    }
    @Override
    public StorageOptions getStorageOptions() {
        return storageOptions;
    }
}
