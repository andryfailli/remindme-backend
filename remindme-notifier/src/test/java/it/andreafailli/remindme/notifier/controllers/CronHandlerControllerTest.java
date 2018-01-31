package it.andreafailli.remindme.notifier.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.common.services.SubscriptionService;
import it.andreafailli.remindme.notifier.models.Notification;
import it.andreafailli.remindme.notifier.services.NotificationService;
import it.andreafailli.remindme.testing.UnitTestCategory;

@RunWith(SpringRunner.class)
@Category(UnitTestCategory.class)
@WebMvcTest(controllers = CronHandlerController.class)
@ActiveProfiles(Profiles.TEST)
public class CronHandlerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReminderService reminderService;
	
	@MockBean
	private SubscriptionService subscriptionService;
	
	@MockBean
	private NotificationService notificationService;
	
	private User user;
	
	private Reminder reminder;
	
	private Subscription subscription1;
	
	private Subscription subscription2;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.user = new User("1");
        
        this.reminder = new Reminder();
        this.reminder.setDate(LocalDateTime.now());
        this.reminder.setTitle("reminder 1");
        this.reminder.setUser(user);
        
        this.subscription1 = new Subscription("1");
        this.subscription1.setUserId(this.user.getId());
        
        this.subscription2 = new Subscription("2");
        this.subscription2.setUserId(this.user.getId());
    }

	@Test
	public void testHandler() throws Exception {
		given(reminderService.list(any(LocalDateTime.class))).willReturn(Arrays.asList(this.reminder));
		given(subscriptionService.list(this.user)).willReturn(Arrays.asList(this.subscription1, this.subscription2));
		
		this.mvc.perform(get(CronHandlerController.BASE_URL))
			.andExpect(status().isOk());
		
		verify(reminderService).list(any(LocalDateTime.class));
		verify(subscriptionService).list(this.user);
		verify(notificationService, times(2)).send(any(Notification.class));
	}

}
