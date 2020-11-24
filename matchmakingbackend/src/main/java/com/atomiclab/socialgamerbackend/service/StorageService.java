package com.atomiclab.socialgamerbackend.service;

import com.google.cloud.storage.StorageOptions;

import org.springframework.stereotype.Service;

@Service
public interface StorageService {
    public StorageOptions getStorageOptions();
}