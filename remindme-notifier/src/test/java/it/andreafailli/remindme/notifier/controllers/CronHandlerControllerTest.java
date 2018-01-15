package it.andreafailli.remindme.notifier.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.notifier.controllers.CronHandlerController;
import it.andreafailli.remindme.testing.UnitTestCategory;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CronHandlerController.class)
@Category(UnitTestCategory.class)
public class CronHandlerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReminderService reminderService;
	
	private Reminder reminder;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.reminder = new Reminder();
        this.reminder.setDate(LocalDateTime.now());
        this.reminder.setTitle("reminder 1");
    }

	@Test
	public void testHandler() throws Exception {
		given(reminderService.list(any(LocalDateTime.class))).willReturn(Arrays.asList(this.reminder));
		this.mvc.perform(get(CronHandlerController.BASE_URL))
			.andExpect(status().isOk());
		verify(reminderService).list(any(LocalDateTime.class));
	}

}
