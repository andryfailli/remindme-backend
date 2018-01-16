package it.andreafailli.remindme.notifier.controllers;

import static io.restassured.RestAssured.given;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import it.andreafailli.remindme.RemindMeNotifierApplication;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.common.services.SubscriptionService;
import it.andreafailli.remindme.common.services.UserService;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemindMeNotifierApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Category(IntegrationTestCategory.class)
public class CronHandlerControllerIT {
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	private Reminder reminder;
	
	private Subscription subscription1;
	
	private Subscription subscription2;
	
	@Before
    public void setUp(){
		this.url = "http://localhost:"+port;
		
		this.user = new User();
		this.userService.insert(this.user);
        
        this.reminder = new Reminder();
        this.reminder.setDate(LocalDateTime.now());
        this.reminder.setTitle("reminder 1");
        this.reminder.setUser(user);
        this.reminderService.insert(this.reminder);
        
        this.subscription1 = new Subscription("1");
        this.subscription1.setUserId(this.user.getId());
        this.subscriptionService.save(this.subscription1);
        
        this.subscription2 = new Subscription("2");
        this.subscription2.setUserId(this.user.getId());
        this.subscriptionService.save(this.subscription2);
    }

	@Test
	public void testHandler() throws Exception {
		given()
			.when()
			.get(this.url + CronHandlerController.BASE_URL)
			.then()
			.statusCode(200);
	}

}
