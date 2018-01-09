package it.andreafailli.remindme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.notifier.controllers.CronHandlerController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CronHandlerController.class)
public class CronHandlerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReminderService reminderService;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testHandler() throws Exception {
		this.mvc.perform(get(CronHandlerController.BASE_URL))
			.andExpect(status().isOk());
	}

}
