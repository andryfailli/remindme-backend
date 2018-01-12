package it.andreafailli.remindme.api.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import it.andreafailli.remindme.RemindMeApiApplication;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.common.services.UserService;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemindMeApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Category(IntegrationTestCategory.class)
public class ReminderControllerIT {
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private UserService userService;
	
	Reminder reminder;
	
	Reminder reminder1;
    
	Reminder reminder2;
	
	@Before
    public void setUp(){
		this.url = "http://localhost:"+port;
		
		this.reminderService.deleteAll();
		this.reminderService.deleteAll();
		
		User u = new User();
		this.userService.insert(u);
        
        this.reminder = new Reminder();
        this.reminder.setDate(new Date());
        this.reminder.setTitle("reminder 1");
        this.reminder.setUser(u);
        
        this.reminder1 = new Reminder();
        this.reminder1.setDate(new Date());
        this.reminder1.setTitle("reminder 1");
        this.reminder1.setArchived(true);
        this.reminder1.setUser(u);
        
        this.reminder2 = new Reminder();
        this.reminder2.setDate(new Date());
        this.reminder2.setTitle("reminder 2");
        this.reminder2.setArchived(false);
        this.reminder2.setUser(u);
        
    }

	@Test
	public void testList() throws Exception {
		this.reminderService.insert(this.reminder1);
        this.reminderService.insert(this.reminder2);
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("$", hasSize(2))
			.body("[0].id", equalTo(this.reminder1.getId()))
			.body("[0].date", equalTo(this.reminder1.getDate().getTime()))
			.body("[0].title", equalTo(this.reminder1.getTitle()))
			.body("[0].archived", equalTo(this.reminder1.isArchived()))
			.body("[0].user.id", equalTo(this.reminder1.getUser().getId()))
			.body("[1].id", equalTo(this.reminder2.getId()))
			.body("[1].date", equalTo(this.reminder2.getDate().getTime()))
			.body("[1].title", equalTo(this.reminder2.getTitle()))
			.body("[1].archived", equalTo(this.reminder2.isArchived()))
			.body("[1].user.id", equalTo(this.reminder2.getUser().getId()));
	}
	
	@Test
	public void testListEmpty() throws Exception {
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("$", hasSize(0));
	}
	
	@Test
	public void testListArchived() throws Exception {
		this.reminderService.insert(this.reminder1);
        this.reminderService.insert(this.reminder2);
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL + "?archived=true")
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("$", hasSize(1))
			.body("[0].id", equalTo(this.reminder1.getId()))
			.body("[0].date", equalTo(this.reminder1.getDate().getTime()))
			.body("[0].title", equalTo(this.reminder1.getTitle()))
			.body("[0].archived", equalTo(this.reminder1.isArchived()))
			.body("[0].user.id", equalTo(this.reminder1.getUser().getId()));
	}
	
	@Test
	public void testListNotArchived() throws Exception {
		this.reminderService.insert(this.reminder1);
        this.reminderService.insert(this.reminder2);
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL + "?archived=false")
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("$", hasSize(1))
			.body("[0].id", equalTo(this.reminder2.getId()))
			.body("[0].date", equalTo(this.reminder2.getDate().getTime()))
			.body("[0].title", equalTo(this.reminder2.getTitle()))
			.body("[0].archived", equalTo(this.reminder2.isArchived()))
			.body("[0].user.id", equalTo(this.reminder2.getUser().getId()));
	}
	
	@Test
	public void testGet() throws Exception {
		this.reminderService.insert(this.reminder1);
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL + "/" + this.reminder1.getId())
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", equalTo(this.reminder1.getId()))
			.body("date", equalTo(this.reminder1.getDate().getTime()))
			.body("title", equalTo(this.reminder1.getTitle()))
			.body("archived", equalTo(this.reminder1.isArchived()))
			.body("user.id", equalTo(this.reminder1.getUser().getId()));
	}
	
	@Test
	public void testGetNotFound() throws Exception {
		given()
			.when()
			.get(this.url + ReminderController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(404);
	}
	
	@Test
	public void testInsert() throws Exception {
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.reminder))
			.when()
			.put(this.url + ReminderController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(201)
			.contentType(ContentType.JSON)
			.body("id", notNullValue())
			.body("date", equalTo(this.reminder.getDate().getTime()))
			.body("title", equalTo(this.reminder.getTitle()))
			.body("archived", equalTo(this.reminder.isArchived()))
			.body("user.id", equalTo(this.reminder.getUser().getId()));
	}
	
	@Test
	public void testInsertWithId() throws Exception {
		this.reminder.setId("0");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.reminder))
			.when()
			.put(this.url + ReminderController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(400);
	}
	
	@Test
	public void testUpdate() throws Exception {
		this.reminderService.insert(reminder);
		this.reminder.setTitle("modified");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.reminder))
			.when()
			.post(this.url + ReminderController.BASE_URL + "/" + this.reminder.getId())
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", notNullValue())
			.body("date", equalTo(this.reminder.getDate().getTime()))
			.body("title", equalTo(this.reminder.getTitle()))
			.body("archived", equalTo(this.reminder.isArchived()))
			.body("user.id", equalTo(this.reminder.getUser().getId()));
		assertThat(this.reminderService.get(this.reminder.getId()).getTitle()).isEqualTo(this.reminder.getTitle());
	}
	
	@Test
	public void testUpdateWithoutEntityId() throws Exception {
		this.reminderService.insert(reminder);
		String id = this.reminder.getId();
		String oldValue = this.reminder.getTitle();
		this.reminder.setTitle("modified");
		this.reminder.setId(null);
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.reminder))
			.when()
			.post(this.url + ReminderController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.reminderService.get(id).getTitle()).isEqualTo(oldValue);
	}
	
	@Test
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.reminderService.insert(reminder);
		String oldValue = this.reminder.getTitle();
		this.reminder.setTitle("modified");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.reminder))
			.when()
			.post(this.url + ReminderController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.reminderService.get(this.reminder.getId()).getTitle()).isEqualTo(oldValue);
	}

	@Test
	public void testDelete() throws Exception {
		this.reminderService.insert(reminder);
		given()
			.when()
			.delete(this.url + ReminderController.BASE_URL + "/" + this.reminder.getId())
			.then()
			.assertThat()
			.statusCode(200);
		assertThat(this.reminderService.get(this.reminder.getId())).isNull();
	}

}