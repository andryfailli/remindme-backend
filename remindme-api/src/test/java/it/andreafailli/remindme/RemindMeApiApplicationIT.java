package it.andreafailli.remindme;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RemindMeApiApplication.class)
@Category(IntegrationTestCategory.class)
public class RemindMeApiApplicationIT {

	@Test
	public void contextLoads() {
	}

}
