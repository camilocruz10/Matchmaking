package com.atomiclab.socialgamerbackend.service.impl;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import com.atomiclab.socialgamerbackend.service.StorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;

import org.springframework.stereotype.Service;
/**
 * Es el servicio que inicia la conexión con Storage (el servicio de almacenamiento de archivos de Firebase) y crea la única instancia para hacer consultas.
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class StorageServiceImpl implements StorageService {
    private static StorageOptions storageOptions;
    /**
     * Es un método privado que inicia la conexión con el Storage mediante el json con las credenciales
     * @throws Exception 
     */
    @PostConstruct
    private static void initializeStorage() throws Exception {
        String projectId = "spring-course-c4e5a";
        FileInputStream serviceAccount = new FileInputStream("spring-course-c4e5a-firebase-adminsdk-f0nr3-9a6cff1a6f.json");
        storageOptions = StorageOptions.newBuilder().setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
    }
    /**
     * retorna la instancia con conexión al servicio de almacenamiento de archivos propio de Firebase.
     * @return instancia con conexión al servicio de almacenamiento
     */
    @Override
    public StorageOptions getStorageOptions() {
        return storageOptions;
    }
}
