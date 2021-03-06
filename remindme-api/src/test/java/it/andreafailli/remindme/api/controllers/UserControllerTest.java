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
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.UserService;
import it.andreafailli.remindme.testing.UnitTestCategory;

@RunWith(SpringRunner.class)
@Category(UnitTestCategory.class)
@WebMvcTest(controllers = {UserController.class, DefaultWebSecurityConfigurerAdapter.class})
@ActiveProfiles(Profiles.TEST)
public class UserControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String USER_MOCK_ID = "USER_MOCK_ID";
	
	private User user;
	
	private User user1;
    
	private User user2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.mvc = MockMvcBuilders
					.webAppContextSetup(this.context)
					.apply(springSecurity())
					.build();
        
        this.user = new User();
        this.user.setEmail("user@example.com");
        this.user.setName("User");
        this.user.setPhotoUrl("https://www.example.com/user.jpg");
        
        this.user1 = new User("1");
        this.user1.setEmail("user1@example.com");
        this.user1.setName("User One");
        this.user1.setPhotoUrl("https://www.example.com/user1.jpg");
        
        this.user2 = new User("2");
        this.user2.setEmail("user2@example.com");
        this.user2.setName("User Two");
        this.user2.setPhotoUrl("https://www.example.com/user2.jpg");
    }

	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testList() throws Exception {
		given(userService.list()).willReturn(Arrays.asList(this.user1, this.user2));
		this.mvc.perform(get(UserController.BASE_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(this.user1.getId())))
			.andExpect(jsonPath("$[0].email", is(this.user1.getEmail())))
			.andExpect(jsonPath("$[0].name", is(this.user1.getName())))
			.andExpect(jsonPath("$[0].photoUrl", is(this.user1.getPhotoUrl())))
			.andExpect(jsonPath("$[1].id", is(this.user2.getId())))
			.andExpect(jsonPath("$[1].email", is(this.user2.getEmail())))
			.andExpect(jsonPath("$[1].name", is(this.user2.getName())))
			.andExpect(jsonPath("$[1].photoUrl", is(this.user2.getPhotoUrl())));
		verify(userService).list();
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testListEmpty() throws Exception {
		given(userService.list()).willReturn(new ArrayList<User>());
		this.mvc.perform(get(UserController.BASE_URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
		verify(userService).list();
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testGet() throws Exception {
		given(userService.get(this.user1.getId())).willReturn(this.user1);
		this.mvc.perform(get(UserController.BASE_URL+"/"+this.user1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.user1.getId())))
			.andExpect(jsonPath("$.email", is(this.user1.getEmail())))
			.andExpect(jsonPath("$.name", is(this.user1.getName())))
			.andExpect(jsonPath("$.photoUrl", is(this.user1.getPhotoUrl())));
		verify(userService).get(this.user1.getId());
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testMe() throws Exception {
		this.user1.setId(USER_MOCK_ID);
		given(userService.get(this.user1.getId())).willReturn(this.user1);
		this.mvc.perform(get(UserController.BASE_URL+"/me").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.user1.getId())))
			.andExpect(jsonPath("$.email", is(this.user1.getEmail())))
			.andExpect(jsonPath("$.name", is(this.user1.getName())))
			.andExpect(jsonPath("$.photoUrl", is(this.user1.getPhotoUrl())));
		verify(userService).get(this.user1.getId());
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testGetNotFound() throws Exception {
		given(userService.get(this.user1.getId())).willReturn(null);
		this.mvc.perform(get(UserController.BASE_URL+"/"+this.user1.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		verify(userService).get(this.user1.getId());
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testInsert() throws Exception {
		given(userService.insert(any(User.class))).willAnswer(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				user.setId("0");
				return user;
			}
		});
		this.mvc.perform(put(UserController.BASE_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.user))
				)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(this.user.getId())))
			.andExpect(jsonPath("$.email", is(this.user.getEmail())))
			.andExpect(jsonPath("$.name", is(this.user.getName())))
			.andExpect(jsonPath("$.photoUrl", is(this.user.getPhotoUrl())));
		verify(userService).insert(any(User.class));
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testInsertWithId() throws Exception {
		this.mvc.perform(put(UserController.BASE_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.user1))
				)
			.andExpect(status().isBadRequest());
		verify(userService, times(0)).insert(any(User.class));
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testUpdate() throws Exception {
		given(userService.update(any(User.class))).willReturn(this.user1);
		this.mvc.perform(post(UserController.BASE_URL+"/"+this.user1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.user1))
				)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(this.user1.getId())))
			.andExpect(jsonPath("$.email", is(this.user1.getEmail())))
			.andExpect(jsonPath("$.name", is(this.user1.getName())))
			.andExpect(jsonPath("$.photoUrl", is(this.user1.getPhotoUrl())));
		verify(userService).update(any(User.class));
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testUpdateWithoutEntityId() throws Exception {
		this.mvc.perform(post(UserController.BASE_URL+"/0")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.user))
				)
			.andExpect(status().isBadRequest());
		verify(userService, times(0)).update(any(User.class));
	}
	
	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testUpdateWithIdsNotMatching() throws Exception {
		this.mvc.perform(post(UserController.BASE_URL+"/"+this.user1.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(this.user2))
				)
			.andExpect(status().isBadRequest());
		verify(userService, times(0)).update(any(User.class));
	}

	@Test
	@WithUserMock(id = USER_MOCK_ID)
	public void testDelete() throws Exception {
		this.mvc.perform(delete(UserController.BASE_URL+"/"+this.user1.getId())
					.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(status().isOk());
		verify(userService).delete(this.user1.getId());
	}
}
