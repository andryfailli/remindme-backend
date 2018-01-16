package it.andreafailli.remindme.notifier.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.andreafailli.remindme.notifier.models.Notification;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class NotificationServiceTest {

	@InjectMocks
    private NotificationService notificationService;

    @Mock
    private RestTemplate restTemplate;
    
    private final String firebaseFcmServerUrl = "http://www.example.com";
    
    private Notification notification;
    
    @Before
    public void setUp() throws IllegalAccessException {
    	MockitoAnnotations.initMocks(this);
    	
    	Field notificationServiceFirebaseFcmServerUrlField = FieldUtils.getField(NotificationService.class, "firebaseFcmServerUrl", true);
    	FieldUtils.writeField(notificationServiceFirebaseFcmServerUrlField, notificationService, firebaseFcmServerUrl);
    	
    	this.notification = new Notification();
		Notification.Data notificationData = new Notification.Data();
		
		this.notification.setTo("1");
		this.notification.setData(notificationData);
		notificationData.setClickAction("http://www.example.com");
		notificationData.setTitle("test");
    }
    
    @Test
	public void testSendOk() {
		when(this.restTemplate.postForEntity(eq(this.firebaseFcmServerUrl), any(), eq(String.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    	
    	this.notificationService.send(this.notification);
    	
    	verify(this.restTemplate).postForEntity(eq(this.firebaseFcmServerUrl), any(), eq(String.class));
	}    
    
    @Test
	public void testSendKo() {
		when(this.restTemplate.postForEntity(eq(this.firebaseFcmServerUrl), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
		this.notificationService.send(this.notification);
		verify(this.restTemplate).postForEntity(eq(this.firebaseFcmServerUrl), any(), eq(String.class));
	}

}
