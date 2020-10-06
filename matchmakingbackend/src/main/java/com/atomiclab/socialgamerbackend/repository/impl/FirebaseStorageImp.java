package com.atomiclab.socialgamerbackend.repository.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.StorageService;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageImp implements FirebaseStorage {
    @Autowired
    StorageService storageService;
    
    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {
        String bucketName = "spring-course-c4e5a.appspot.com";
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);
        Storage storage = storageService.getStorageOptions().getService(); 
        BlobId blobId = BlobId.of(bucketName, folder+objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(filePath));
        
        return folder.concat(objectName);
    }
    @Override
    public byte[] downloadFile(String fileName) throws Exception {
        
        String bucketName = "spring-course-c4e5a.appspot.com";
        Storage storage = storageService.getStorageOptions().getService(); 
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        return content;

    }
    private File convertMultiPartToFile( MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();

        return convertedFile;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
