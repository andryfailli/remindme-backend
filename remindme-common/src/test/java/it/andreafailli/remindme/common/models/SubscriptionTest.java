package it.andreafailli.remindme.common.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class SubscriptionTest {

	private Subscription s;
	
	@Before
	public void setUp() {
		this.s = new Subscription();
	}
	
	@Test
	public void testConstructorNoArgs() {
		Subscription entity = new Subscription();
		assertThat(entity.getId()).isNull();
	}
	
	@Test
	public void testConstructorWithId() {
		String id = "id";
		Subscription entity = new Subscription(id);
		assertThat(entity.getId()).isEqualTo(id);
	}
	
	@Test
	public void testUserId() {
		String value = "value";
		this.s.setUserId(value);
		assertThat(this.s.getUserId()).isEqualTo(value);
	}
	
}
