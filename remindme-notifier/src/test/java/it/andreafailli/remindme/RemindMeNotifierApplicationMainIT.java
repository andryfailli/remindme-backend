package it.andreafailli.remindme;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.IntegrationTestCategory;

@Category(IntegrationTestCategory.class)
public class RemindMeNotifierApplicationMainIT {

	@Test
	public void main() throws IOException {
		RemindMeNotifierApplication.main(new String[] {});
	}

}
