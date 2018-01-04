package it.andreafailli.remindme.common.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User u;
	
	@Before
	public void setUp() {
		this.u = new User();
	}
	
	@Test
	public void testConstructorNoArgs() {
		BaseEntity entity = new User();
		assertNull(entity.getId());
	}
	
	@Test
	public void testConstructorWithId() {
		String id = "id";
		BaseEntity entity = new User(id);
		assertEquals(id, entity.getId());
	}
	
	@Test
	public void testName() {
		String value = "value";
		this.u.setName(value);
		assertEquals(value, this.u.getName());
	}
	
	@Test
	public void testEmail() {
		String value = "value";
		this.u.setEmail(value);
		assertEquals(value, this.u.getEmail());
	}
	
	@Test
	public void testPhotoUrl() {
		String value = "value";
		this.u.setPhotoUrl(value);
		assertEquals(value, this.u.getPhotoUrl());
	}
	
}
