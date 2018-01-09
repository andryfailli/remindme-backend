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
	public void testEqualsEntityVersusEntityNoArgConstructor() {
		assertThat(this.e1).isNotEqualTo(new FakeEntity());
	}
	
	@Test
	public void testEqualEntityNoArgConstructorVersusEntityNoArgConstructor() {
		assertThat(new FakeEntity()).isNotEqualTo(new FakeEntity());
	}
	
	@Test
	public void testEqualsEntityNoArgConstructorVersusEntity() {
		assertThat(new FakeEntity()).isNotEqualTo(this.e1);
	}
	
	@Test
	public void testEqualsEntityVersusAnotherEntityWithSameId() {
		assertThat(this.e1).isEqualTo(new FakeEntity(this.e1.getId()));
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
	
	@Test
	public void testHashCodeDifferentEntities() {
		assertThat(this.e1.hashCode()).isNotEqualTo(this.e2.hashCode());
	}
	
	@Test
	public void testHashCodeEntityNoArgConstructorVersusEntity() {
		assertThat(new FakeEntity().hashCode()).isNotEqualTo(this.e1.hashCode());
	}
	
	@Test
	public void testHashCodeEntityVersusAnotherEntityWithSameId() {
		assertThat(this.e1.hashCode()).isEqualTo(new FakeEntity(this.e1.getId()).hashCode());
	}
	
}
