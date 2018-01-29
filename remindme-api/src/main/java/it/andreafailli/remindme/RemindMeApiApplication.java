package it.andreafailli.remindme;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import it.andreafailli.remindme.api.auth.FirebaseAuthFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebSecurity
@EnableSwagger2
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
	
	@Bean
    public Docket swaggerConfiguration() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .apis(RequestHandlerSelectors.basePackage("it.andreafailli.remindme.api.controllers"))
          .paths(PathSelectors.any())
          .build()
          .securityContexts(Lists.newArrayList(SecurityContext.builder()
      	        .securityReferences(Lists.newArrayList(new SecurityReference(FirebaseAuthFilter.HEADER_NAME, new AuthorizationScope[] {})))
    	        .forPaths(PathSelectors.regex("/api/*"))
    	        .build()))
          .securitySchemes(Lists.newArrayList(new ApiKey(FirebaseAuthFilter.HEADER_NAME, FirebaseAuthFilter.HEADER_NAME, ApiKeyVehicle.HEADER.getValue())));
    }

}
