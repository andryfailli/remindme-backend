package it.andreafailli.remindme;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	
	public static void initFirebase() throws IOException {
    	InputStream inputStream = RemindMeApiApplication.class.getClassLoader().getResourceAsStream("firebase-server-key.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(inputStream))
		  .setDatabaseUrl("https://glass-crossing.firebaseio.com")
		  .build();
		if (FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options);
 
    }
}
