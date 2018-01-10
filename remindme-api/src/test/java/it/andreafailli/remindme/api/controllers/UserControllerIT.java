package it.andreafailli.remindme.api.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;

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
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.UserService;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemindMeApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Category(IntegrationTestCategory.class)
public class UserControllerIT {
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	@Autowired
	private UserService userService;
	
	User user;
	
	User user1;
    
	User user2;
	
	@Before
    public void setUp(){
		this.url = "http://localhost:"+port;
		
		this.userService.deleteAll();
		
		this.user = new User();
        this.user.setEmail("user@example.com");
        this.user.setName("User");
        this.user.setPhotoUrl("https://www.example.com/user.jpg");
        
        this.user1 = new User();
        this.user1.setEmail("user1@example.com");
        this.user1.setName("User One");
        this.user1.setPhotoUrl("https://www.example.com/user1.jpg");
        
        this.user2 = new User();
        this.user2.setEmail("user2@example.com");
        this.user2.setName("User Two");
        this.user2.setPhotoUrl("https://www.example.com/user2.jpg");
        
    }

	@Test
	public void testList() throws Exception {
		this.userService.insert(this.user1);
        this.userService.insert(this.user2);
		given()
			.when()
			.get(this.url + UserController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("[0].id", equalTo(this.user1.getId()))
			.body("[0].email", equalTo(this.user1.getEmail()))
			.body("[0].name", equalTo(this.user1.getName()))
			.body("[0].photoUrl", equalTo(this.user1.getPhotoUrl()))
			.body("[1].id", equalTo(this.user2.getId()))
			.body("[1].email", equalTo(this.user2.getEmail()))
			.body("[1].name", equalTo(this.user2.getName()))
			.body("[1].photoUrl", equalTo(this.user2.getPhotoUrl()));
	}
	
	@Test
	public void testListEmpty() throws Exception {
		given()
			.when()
			.get(this.url + UserController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("$", empty());
	}
	
	@Test
	public void testGet() throws Exception {
		this.userService.insert(this.user1);
		given()
			.when()
			.get(this.url + UserController.BASE_URL + "/" + this.user1.getId())
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", equalTo(this.user1.getId()))
			.body("email", equalTo(this.user1.getEmail()))
			.body("name", equalTo(this.user1.getName()))
			.body("photoUrl", equalTo(this.user1.getPhotoUrl()));
	}
	
	@Test
	public void testGetNotFound() throws Exception {
		given()
			.when()
			.get(this.url + UserController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(404);
	}
	
	@Test
	public void testInsert() throws Exception {
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.user))
			.when()
			.put(this.url + UserController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(201)
			.contentType(ContentType.JSON)
			.body("id", notNullValue())
			.body("email", equalTo(this.user.getEmail()))
			.body("name", equalTo(this.user.getName()))
			.body("photoUrl", equalTo(this.user.getPhotoUrl()));
	}
	
	@Test
	public void testInsertWithId() throws Exception {
		this.user.setId("0");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.user))
			.when()
			.put(this.url + UserController.BASE_URL)
			.then()
			.assertThat()
			.statusCode(400);
	}
	
	@Test
	public void testUpdate() throws Exception {
		this.userService.insert(user);
		this.user.setName("modified");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.user))
			.when()
			.post(this.url + UserController.BASE_URL + "/" + this.user.getId())
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", notNullValue())
			.body("email", equalTo(this.user.getEmail()))
			.body("name", equalTo(this.user.getName()))
			.body("photoUrl", equalTo(this.user.getPhotoUrl()));
		assertThat(this.userService.get(this.user.getId()).getName()).isEqualTo(this.user.getName());
	}
	
	@Test
	public void testUpdateWithoutEntityId() throws Exception {
		this.userService.insert(user);
		String id = this.user.getId();
		String oldValue = this.user.getName();
		this.user.setName("modified");
		this.user.setId(null);
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.user))
			.when()
			.post(this.url + UserController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.userService.get(id).getName()).isEqualTo(oldValue);
	}
	
	@Test
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.userService.insert(user);
		String oldValue = this.user.getName();
		this.user.setName("modified");
		given()
			.contentType(ContentType.JSON)
			.body(new ObjectMapper().writeValueAsString(this.user))
			.when()
			.post(this.url + UserController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.userService.get(this.user.getId()).getName()).isEqualTo(oldValue);
	}

	@Test
	public void testDelete() throws Exception {
		this.userService.insert(user);
		given()
			.when()
			.delete(this.url + UserController.BASE_URL + "/" + this.user.getId())
			.then()
			.assertThat()
			.statusCode(200);
		assertThat(this.userService.get(this.user.getId())).isNull();
	}

}
