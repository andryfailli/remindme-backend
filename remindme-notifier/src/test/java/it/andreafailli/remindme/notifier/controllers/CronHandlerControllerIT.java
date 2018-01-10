package it.andreafailli.remindme.notifier.controllers;

import static io.restassured.RestAssured.given;

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
import it.andreafailli.remindme.common.services.ReminderService;
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
	
	@Before
    public void setUp(){
		this.url = "http://localhost:"+port;
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
