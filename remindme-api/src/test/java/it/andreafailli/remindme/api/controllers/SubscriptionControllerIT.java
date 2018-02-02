package it.andreafailli.remindme.api.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.RemindMeApiApplication;
import it.andreafailli.remindme.api.auth.FirebaseAuthFilter;
import it.andreafailli.remindme.auth.FirebaseIdTokenAuthenticatorMock;
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.services.SubscriptionService;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@Category(IntegrationTestCategory.class)
@SpringBootTest(classes = {RemindMeApiApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Profiles.TEST)
public class SubscriptionControllerIT {
	
	@LocalServerPort
	private int port;
	
	private String url;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Subscription subscription;
		
	@Before
    public void setUp() throws InterruptedException, ExecutionException{
		this.url = "http://localhost:"+port;
		
		this.subscriptionService.deleteAll();
		
		this.subscription = new Subscription("1");
        this.subscription.setUserId("a");        
    }
	
	@Test
	public void testUpdate() throws Exception {
		this.subscriptionService.insert(subscription);
		this.subscription.setUserId("modified");
		given()
			.header(FirebaseAuthFilter.HEADER_NAME, FirebaseIdTokenAuthenticatorMock.ID_TOKEN_MOCK)
			.contentType(ContentType.JSON)
			.body(this.objectMapper.writeValueAsString(this.subscription))	
			.when()
			.post(this.url + SubscriptionController.BASE_URL + "/" + this.subscription.getId())
			.then()
			.assertThat()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.body("id", notNullValue())
			.body("userId", equalTo(this.subscription.getUserId()));
		assertThat(this.subscriptionService.get(this.subscription.getId()).getUserId()).isEqualTo(this.subscription.getUserId());
	}
	
	@Test
	public void testUpdateWithoutEntityId() throws Exception {
		this.subscriptionService.insert(subscription);
		String id = this.subscription.getId();
		String oldValue = this.subscription.getUserId();
		this.subscription.setUserId("modified");
		this.subscription.setId(null);
		given()
			.header(FirebaseAuthFilter.HEADER_NAME, FirebaseIdTokenAuthenticatorMock.ID_TOKEN_MOCK)
			.contentType(ContentType.JSON)
			.body(this.objectMapper.writeValueAsString(this.subscription))
			.when()
			.post(this.url + SubscriptionController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.subscriptionService.get(id).getUserId()).isEqualTo(oldValue);
	}
	
	@Test
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.subscriptionService.insert(subscription);
		String oldValue = this.subscription.getUserId();
		this.subscription.setUserId("modified");
		given()
			.header(FirebaseAuthFilter.HEADER_NAME, FirebaseIdTokenAuthenticatorMock.ID_TOKEN_MOCK)
			.contentType(ContentType.JSON)
			.body(this.objectMapper.writeValueAsString(this.subscription))
			.when()
			.post(this.url + SubscriptionController.BASE_URL + "/0")
			.then()
			.assertThat()
			.statusCode(400);
		assertThat(this.subscriptionService.get(this.subscription.getId()).getUserId()).isEqualTo(oldValue);
	}

	@Test
	public void testDelete() throws Exception {
		this.subscriptionService.insert(subscription);
		given()
			.header(FirebaseAuthFilter.HEADER_NAME, FirebaseIdTokenAuthenticatorMock.ID_TOKEN_MOCK)
			.when()
			.delete(this.url + SubscriptionController.BASE_URL + "/" + this.subscription.getId())
			.then()
			.assertThat()
			.statusCode(200);
		assertThat(this.subscriptionService.get(this.subscription.getId())).isNull();
	}

}
