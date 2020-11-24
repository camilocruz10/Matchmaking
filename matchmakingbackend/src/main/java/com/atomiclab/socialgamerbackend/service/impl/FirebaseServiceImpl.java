package com.atomiclab.socialgamerbackend.service.impl;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    /**
     * inicia la conexión con la base de datos mediante el json 
     * @throws IOException 
     */
  @PostConstruct
  private void init() throws IOException {
    FileInputStream serviceAccount = new FileInputStream("spring-course-c4e5a-firebase-adminsdk-f0nr3-9a6cff1a6f.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://spring-course-c4e5a.firebaseio.com")
        .build();
    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }
  }
  /**
   * instancia con conexión al servicio de autenticación propio de Firebase.
   * @return instancia de la conexión
   */
  @Override
  public FirebaseAuth getFirebasAuth() {
    return FirebaseAuth.getInstance();
  }
  /**
   * instancia con conexión al servicio de base de datos (Firestore) propio de Firebase
   * @return la instancia con conexión al servicio de base de datos
   */
  @Override
  public Firestore getFirestore() {
    return FirestoreClient.getFirestore();
  }
}
