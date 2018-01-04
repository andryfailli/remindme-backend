package it.andreafailli.remindme.common.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {
	
	public class FakeEntity extends BaseEntity {
		
		public FakeEntity() {
			super();
		}
		
		public FakeEntity(String id) {
			super(id);
		}
		
	}

	private FakeEntity e1, e2;
	
	@Before
	public void setUp() {
		this.e1 = new FakeEntity("1");
		this.e2 = new FakeEntity("2");
	}
	
	@Test
	public void testId() {
		String value = "value";
		FakeEntity e = new FakeEntity();
		e.setId(value);
		assertEquals(value, e.getId());
	}
	
	@Test
	public void testEquals() {
		assertEquals(this.e1, this.e1);
		assertEquals(this.e2, this.e2);
	}
	
	@Test
	public void testNotEquals() {
		assertNotEquals(this.e1, this.e2);
	}
	
	@Test
	public void testNotEqualsNull() {
		assertNotEquals(this.e1, null);
	}
	
	@Test
	public void testNotEqualsObject() {
		assertNotEquals(this.e1, new Object());
	}
	
	@Test
	public void testToString() {
		assertNotNull(this.e1.toString());
	}
	
}
