package it.andreafailli.remindme;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import it.andreafailli.remindme.testing.IntegrationTestCategory;

@Category(IntegrationTestCategory.class)
public class RemindMeApiApplicationMainIT {

	@Test
	public void main() throws IOException {
		RemindMeApiApplication.main(new String[] {});
	}

}
