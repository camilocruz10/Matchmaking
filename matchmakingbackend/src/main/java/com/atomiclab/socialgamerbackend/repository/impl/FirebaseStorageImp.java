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
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
/**
 * Este servicio maneja los métodos relacionados al almacenamiento de archivos en el servicio de Storage
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class FirebaseStorageImp implements FirebaseStorage {
    @Autowired
    StorageService storageService;
    /**
     * Recibe un multipart file y la dirección en que se guardara este archivo
     * @param multipartFile Objeto de tipo multipart
     * @param folder Nombre de la carpeta donde se va a a subir
     * @return La dirección del archivo
     * @throws IOException 
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {
        String bucketName = "spring-course-c4e5a.appspot.com";
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);
        Storage storage = storageService.getStorageOptions().getService();
        BlobId blobId = BlobId.of(bucketName, folder + objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(filePath));
        return folder.concat(objectName);
    }
    /**
     * Recibe la dirección en que se guardó este archivo y retorna un string con la lista de byte de la imagen codificada en base64
     * @param fileName ubicación del archivo
     * @return retorna la imagen codificada
     * @throws Exception 
     */
    @Override
    public String downloadFile(String fileName) throws Exception {

        String bucketName = "spring-course-c4e5a.appspot.com";
        Storage storage = storageService.getStorageOptions().getService();
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);
        byte[] encoded = Base64Utils.encode(content);
        return new String(encoded); // Outputs "SGVsbG8="

        /*
         * final ByteArrayResource byteArrayResource = new ByteArrayResource(content);
         * 
         * return ResponseEntity .ok() .contentLength(content.length)
         * .header("Content-type", "application/octet-stream")
         * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName
         * + "\"") .body(byteArrayResource);
         */

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();

        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    @Override
    public boolean deleteFile(String fileName) {
        String bucketName = "spring-course-c4e5a.appspot.com";
        Storage storage = storageService.getStorageOptions().getService();
        return storage.delete(BlobId.of(bucketName, fileName));
    }
}
