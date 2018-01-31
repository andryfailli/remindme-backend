package it.andreafailli.remindme;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.IntegrationTestCategory;

@Category(IntegrationTestCategory.class)
public class RemindMeCommonTestApplicationMainIT {

	@Test
	public void main() {
		RemindMeCommonTestApplication.main(new String[] {});
	}

}
