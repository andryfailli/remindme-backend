package it.andreafailli.remindme;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableWebSecurity
public class RemindMeApiApplication {

	public static void main(String[] args) throws IOException {
		initFirebase();
		SpringApplication.run(RemindMeApiApplication.class, args);
	}
	
	private static void initFirebase() throws IOException {
		FileInputStream serviceAccount = new FileInputStream(new ClassPathResource("firebase-server-key.json").getFile());

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .setDatabaseUrl("https://glass-crossing.firebaseio.com")
		  .build();
		if (FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options);
	}
}
