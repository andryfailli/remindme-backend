package it.andreafailli.remindme;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@Category(IntegrationTestCategory.class)
@SpringBootTest(classes = RemindMeApiApplication.class)
@ActiveProfiles(Profiles.TEST)
public class RemindMeApiApplicationIT {

	@Test
	public void contextLoads() {
	}

}
