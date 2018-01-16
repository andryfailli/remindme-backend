package it.andreafailli.remindme.notifier.services;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.andreafailli.remindme.notifier.models.Notification;

@Service
public class NotificationService {
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(NotificationService.class);
	
	private RestTemplate restTemplate;
	
	@Value("${firebase.fcmServerUrl}")
	private String firebaseFcmServerUrl;
	
	@Value("${firebase.config.serverKey}")
	private String firebaseConfigServerKey;

	public NotificationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public NotificationService() {
		this(new RestTemplate());
	}
	
	public void send(Notification notification) {
		LOGGER.entry(notification);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "key="+this.firebaseConfigServerKey);
		
		try {
			restTemplate.postForEntity(this.firebaseFcmServerUrl, new HttpEntity<Notification>(notification, headers), String.class);
			LOGGER.info("Notification sent.");
		} catch (HttpClientErrorException exception) {
			LOGGER.error("An error occurred while sending notification: "+exception.toString());
		}
		
		LOGGER.exit();
	}

}
