package it.andreafailli.remindme.common.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class ReminderTest {

	private Reminder r;
	
	@Before
	public void setUp() {
		this.r = new Reminder();
	}
	
	@Test
	public void testConstructorNoArgs() {
		Reminder entity = new Reminder();
		assertThat(entity.getId()).isNull();
	}
	
	@Test
	public void testConstructorWithId() {
		String id = "id";
		Reminder entity = new Reminder(id);
		assertThat(entity.getId()).isEqualTo(id);
	}
	
	@Test
	public void testTitle() {
		String value = "value";
		this.r.setTitle(value);
		assertThat(this.r.getTitle()).isEqualTo(value);
	}
	
	@Test
	public void testDate() {
		LocalDateTime value = LocalDateTime.now();
		this.r.setDate(value);
		assertThat(this.r.getDate()).isEqualTo(value);
	}
	
	@Test
	public void testUser() {
		User value = new User();
		this.r.setUser(value);
		assertThat(this.r.getUser()).isEqualTo(value);
	}
	
	@Test
	public void testArchived() {
		boolean value = true;
		this.r.setArchived(value);
		assertThat(this.r.isArchived()).isEqualTo(value);
	}
	
}
