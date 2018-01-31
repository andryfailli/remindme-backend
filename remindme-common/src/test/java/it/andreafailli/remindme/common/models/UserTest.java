package it.andreafailli.remindme.common.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class UserTest {

	private User u;
	
	@Before
	public void setUp() {
		this.u = new User();
	}
	
	@Test
	public void testConstructorNoArgs() {
		BaseEntity entity = new User();
		assertThat(entity.getId()).isNull();
	}
	
	@Test
	public void testConstructorWithId() {
		String id = "id";
		BaseEntity entity = new User(id);
		assertThat(entity.getId()).isEqualTo(id);
	}
	
	@Test
	public void testName() {
		String value = "value";
		this.u.setName(value);
		assertThat(this.u.getName()).isEqualTo(value);
	}
	
	@Test
	public void testEmail() {
		String value = "value";
		this.u.setEmail(value);
		assertThat(this.u.getEmail()).isEqualTo(value);
	}
	
	@Test
	public void testPhotoUrl() {
		String value = "value";
		this.u.setPhotoUrl(value);
		assertThat(this.u.getPhotoUrl()).isEqualTo(value);
	}
	
}
