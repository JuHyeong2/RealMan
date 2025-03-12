package com.example.demo.common.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
	
	@PostConstruct
	public void firestore() throws IOException {
		
		FileInputStream serviceAccount =  
				new FileInputStream("src/main/resources/realmanfirestore-firebase-adminsdk-fbsvc-42ed42e9d5.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .build();

		FirebaseApp.initializeApp(options);

	}
}
