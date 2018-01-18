package it.andreafailli.remindme.notifier.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.RemindMeNotifierApplication;
import it.andreafailli.remindme.notifier.models.Notification;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@Category(IntegrationTestCategory.class)
@SpringBootTest(classes = RemindMeNotifierApplication.class)
@ActiveProfiles(Profiles.TEST)
public class NotificationServiceIT {
	
	@Autowired
	private NotificationService notificationService;
	
	private Notification notification;

    @Before
    public void setUp(){
    	
    	this.notification = new Notification();
		Notification.Data notificationData = new Notification.Data();
		
		this.notification.setTo("1");
		this.notification.setData(notificationData);
		notificationData.setClickAction("http://www.example.com");
		notificationData.setTitle("test");
    }
    
    @Test
	public void testSend() {
    	this.notificationService.send(this.notification);
	}

}
