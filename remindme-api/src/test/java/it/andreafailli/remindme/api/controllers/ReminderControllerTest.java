package it.andreafailli.remindme.api.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.api.auth.DefaultWebSecurityConfigurerAdapter;
import it.andreafailli.remindme.auth.WithUserMock;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.testing.UnitTestCategory;

@RunWith(SpringRunner.class)
@Category(UnitTestCategory.class)
@WebMvcTest(controllers = {ReminderController.class, DefaultWebSecurityConfigurerAdapter.class})
@ActiveProfiles(Profiles.TEST)
public class ReminderControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@MockBean
	private ReminderService reminderService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Reminder reminder;
	
	private Reminder reminder1;
    
	private Reminder reminder2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.mvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.apply(springSecurity())
				.build();
        
        User u = new User("1");
        
        this.reminder = new Reminder();
        this.reminder.setDate(LocalDateTime.now());
        this.reminder.setTitle("reminder 1");
        this.reminder.setUser(u);
        
        this.reminder1 = new Reminder("1");
        this.reminder1.setDate(LocalDateTime.now());
        this.reminder1.setTitle("reminder 1");
        this.reminder1.setArchived(true);
        this.reminder1.setUser(u);
        
        this.reminder2 = new Reminder("2");
        this.reminder2.setDate(LocalDateTime.now());
        this.reminder2.setTitle("reminder 2");
        this.reminder2.setArchived(false);
        this.reminder2.setUser(u);
    }

	@Test
	@WithUserMock
	public void testList() throws Exception {
		given(reminderService.list()).willReturn(Arrays.asList(this.reminder1, this.reminder2));
		this.mvc.perform(get(ReminderController.BASE_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(this.reminder1.getId())))
			.andExpect(jsonPath("$[0].title", is(this.reminder1.getTitle())))
			.andExpect(jsonPath("$[0].date", is(this.reminder1.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$[0].archived", is(this.reminder1.isArchived())))
			.andExpect(jsonPath("$[0].user.id", is(this.reminder1.getUser().getId())))
			.andExpect(jsonPath("$[1].id", is(this.reminder2.getId())))
			.andExpect(jsonPath("$[1].title", is(this.reminder2.getTitle())))
			.andExpect(jsonPath("$[1].date", is(this.reminder2.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$[1].archived", is(this.reminder2.isArchived())))
			.andExpect(jsonPath("$[1].user.id", is(this.reminder2.getUser().getId())));
		verify(reminderService).list();
	}
	
	@Test
	@WithUserMock
	public void testListByArchivedTrue() throws Exception {
		given(reminderService.list(true)).willReturn(Arrays.asList(this.reminder1));
		this.mvc.perform(get(ReminderController.BASE_URL + "?archived=true").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(this.reminder1.getId())))
			.andExpect(jsonPath("$[0].title", is(this.reminder1.getTitle())))
			.andExpect(jsonPath("$[0].date", is(this.reminder1.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$[0].user.id", is(this.reminder1.getUser().getId())));
		verify(reminderService).list(true);
	}
	
	@Test
	@WithUserMock
	public void testListByArchivedFalse() throws Exception {
		given(reminderService.list(false)).willReturn(Arrays.asList(this.reminder2));
		this.mvc.perform(get(ReminderController.BASE_URL + "?archived=false").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(this.reminder2.getId())))
			.andExpect(jsonPath("$[0].title", is(this.reminder2.getTitle())))
			.andExpect(jsonPath("$[0].date", is(this.reminder2.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$[0].user.id", is(this.reminder2.getUser().getId())));
		verify(reminderService).list(false);
	}
	
	@Test
	@WithUserMock
	public void testListEmpty() throws Exception {
		given(reminderService.list()).willReturn(new ArrayList<Reminder>());
		this.mvc.perform(get(ReminderController.BASE_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
		verify(reminderService).list();
	}
	
	@Test
	@WithUserMock
	public void testGet() throws Exception {
		given(reminderService.get(this.reminder1.getId())).willReturn(this.reminder1);
		this.mvc.perform(get(ReminderController.BASE_URL+"/"+this.reminder1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.reminder1.getId())))
			.andExpect(jsonPath("$.title", is(this.reminder1.getTitle())))
			.andExpect(jsonPath("$.date", is(this.reminder1.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$.archived", is(this.reminder1.isArchived())))
			.andExpect(jsonPath("$.user.id", is(this.reminder1.getUser().getId())));
		verify(reminderService).get(this.reminder1.getId());
	}
	
	@Test
	@WithUserMock
	public void testGetNotFound() throws Exception {
		given(reminderService.get(this.reminder1.getId())).willReturn(null);
		this.mvc.perform(get(ReminderController.BASE_URL+"/"+this.reminder1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		verify(reminderService).get(this.reminder1.getId());
	}
	
	@Test
	@WithUserMock
	public void testInsert() throws Exception {
		given(reminderService.insert(any(Reminder.class))).willAnswer(new Answer<Reminder>() {
			@Override
			public Reminder answer(InvocationOnMock invocation) throws Throwable {
				reminder.setId("0");
				return reminder;
			}
		});
		this.mvc.perform(put(ReminderController.BASE_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.reminder))
				)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(this.reminder.getId())))
			.andExpect(jsonPath("$.title", is(this.reminder.getTitle())))
			.andExpect(jsonPath("$.date", is(this.reminder.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$.archived", is(this.reminder.isArchived())))
			.andExpect(jsonPath("$.user.id", is(this.reminder.getUser().getId())));
		verify(reminderService).insert(any(Reminder.class));
	}
	
	@Test
	@WithUserMock
	public void testInsertWithId() throws Exception {
		this.mvc.perform(put(ReminderController.BASE_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.reminder1))
				)
			.andExpect(status().isBadRequest());
		verify(reminderService, times(0)).insert(any(Reminder.class));
	}
	
	@Test
	@WithUserMock
	public void testUpdate() throws Exception {
		given(reminderService.update(any(Reminder.class))).willReturn(this.reminder1);
		this.mvc.perform(post(ReminderController.BASE_URL+"/"+this.reminder1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.reminder1))
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.reminder1.getId())))
			.andExpect(jsonPath("$.title", is(this.reminder1.getTitle())))
			.andExpect(jsonPath("$.date", is(this.reminder1.getDate().format(DateTimeFormatter.ISO_DATE_TIME))))
			.andExpect(jsonPath("$.archived", is(this.reminder1.isArchived())))
			.andExpect(jsonPath("$.user.id", is(this.reminder1.getUser().getId())));
		verify(reminderService).update(any(Reminder.class));
	}
	
	@Test
	@WithUserMock
	public void testUpdateWithoutEntityId() throws Exception {
		this.mvc.perform(post(ReminderController.BASE_URL+"/0")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.reminder))
				)
			.andExpect(status().isBadRequest());
		verify(reminderService, times(0)).update(any(Reminder.class));
	}
	
	@Test
	@WithUserMock
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.mvc.perform(post(ReminderController.BASE_URL+"/"+this.reminder1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.reminder2))
				)
			.andExpect(status().isBadRequest());
		verify(reminderService, times(0)).update(any(Reminder.class));
	}

	@Test
	@WithUserMock
	public void testDelete() throws Exception {
		this.mvc.perform(delete(ReminderController.BASE_URL+"/"+this.reminder1.getId())
					.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk());
		verify(reminderService).delete(this.reminder1.getId());
	}
}
