package it.andreafailli.remindme.common.models;

import static org.assertj.core.api.Assertions.assertThat;

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

	private FakeEntity e1;
	
	private FakeEntity e2;
	
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
		assertThat(e.getId()).isEqualTo(value);
	}
	
	@Test
	public void testEquals() {
		assertThat(this.e1).isEqualTo(this.e1);
	}
	
	@Test
	public void testEqualsDifferentEntities() {
		assertThat(this.e1).isNotEqualTo(this.e2);
	}
	
	@Test
	public void testEqualsDifferentObjects() {
		assertThat(this.e1).isNotEqualTo(new Object());
	}
	
	@Test
	public void testEqualsNull() {
		assertThat(this.e1).isNotEqualTo(null);
	}
	
	@Test
	public void testToStringNotNull() {
		assertThat(this.e1.toString()).isNotNull();
	}
	
	@Test
	public void testToStringNotEmpty() {
		assertThat(this.e1.toString()).isNotEmpty();
	}
	
}
