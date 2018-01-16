package it.andreafailli.remindme.api.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.services.SubscriptionService;
import it.andreafailli.remindme.testing.UnitTestCategory;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SubscriptionController.class)
@Category(UnitTestCategory.class)
public class SubscriptionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SubscriptionService subscriptionService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Subscription subscription;
	
	private Subscription subscription1;
    
	private Subscription subscription2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.subscription = new Subscription();
        this.subscription.setUserId("a");
        
        this.subscription1 = new Subscription("1");
        this.subscription.setUserId("b");
        
        this.subscription2 = new Subscription("2");
        this.subscription.setUserId("c");
    }
	
	@Test
	public void testUpdate() throws Exception {
		given(subscriptionService.save(any(Subscription.class))).willReturn(this.subscription1);
		this.mvc.perform(post(SubscriptionController.BASE_URL+"/"+this.subscription1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.subscription1))
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.subscription1.getId())))
			.andExpect(jsonPath("$.userId", is(this.subscription1.getUserId())));
		verify(subscriptionService).save(any(Subscription.class));
	}
	
	@Test
	public void testUpdateWithoutEntityId() throws Exception {
		this.mvc.perform(post(SubscriptionController.BASE_URL+"/0")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.subscription))
				)
			.andExpect(status().isBadRequest());
		verify(subscriptionService, times(0)).save(any(Subscription.class));
	}
	
	@Test
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.mvc.perform(post(SubscriptionController.BASE_URL+"/"+this.subscription1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.subscription2))
				)
			.andExpect(status().isBadRequest());
		verify(subscriptionService, times(0)).save(any(Subscription.class));
	}

	@Test
	public void testDelete() throws Exception {
		this.mvc.perform(delete(SubscriptionController.BASE_URL+"/"+this.subscription1.getId())
					.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk());
		verify(subscriptionService).delete(this.subscription1.getId());
	}
}
