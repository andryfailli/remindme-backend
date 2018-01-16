package it.andreafailli.remindme.notifier.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class NotificationTest {

	private Notification n;
	
	@Before
	public void setUp() {
		this.n = new Notification();
	}
	
	@Test
	public void testTo() {
		String value = "value";
		this.n.setTo(value);
		assertThat(this.n.getTo()).isEqualTo(value);
	}
	
	@Test
	public void testData() {
		Notification.Data value = new Notification.Data();
		this.n.setData(value);
		assertThat(this.n.getData()).isEqualTo(value);
	}
	
	@Test
	public void testDataTitle() {
		String value = "value";
		Notification.Data data = new Notification.Data();
		data.setTitle(value);
		this.n.setData(data);
		assertThat(this.n.getData().getTitle()).isEqualTo(value);
	}
	
	@Test
	public void testDataClick_action() {
		String value = "value";
		Notification.Data data = new Notification.Data();
		data.setClickAction(value);
		this.n.setData(data);
		assertThat(this.n.getData().getClickAction()).isEqualTo(value);
	}
	
}
