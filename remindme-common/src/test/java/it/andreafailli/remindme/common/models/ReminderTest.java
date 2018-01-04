package it.andreafailli.remindme.common.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ReminderTest {

	private Reminder r;
	
	@Before
	public void setUp() {
		this.r = new Reminder();
	}
	
	@Test
	public void testConstructorNoArgs() {
		Reminder entity = new Reminder();
		assertNull(entity.getId());
	}
	
	@Test
	public void testConstructorWithId() {
		String id = "id";
		Reminder entity = new Reminder(id);
		assertEquals(id, entity.getId());
	}
	
	@Test
	public void testTitle() {
		String value = "value";
		this.r.setTitle(value);
		assertEquals(value, this.r.getTitle());
	}
	
	@Test
	public void testDate() {
		Date value = new Date();
		this.r.setDate(value);
		assertEquals(value, this.r.getDate());
	}
	
	@Test
	public void testUser() {
		User value = new User();
		this.r.setUser(value);
		assertEquals(value, this.r.getUser());
	}
	
}
