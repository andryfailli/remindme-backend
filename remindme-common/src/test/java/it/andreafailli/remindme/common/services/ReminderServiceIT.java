package it.andreafailli.remindme.common.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.RemindMeCommonTestApplication;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.repositories.IReminderRepository;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@Category(IntegrationTestCategory.class)
@SpringBootTest(classes = RemindMeCommonTestApplication.class)
@ActiveProfiles(Profiles.TEST)
public class ReminderServiceIT {
	
	@Autowired
	private ReminderService entityService;

	@Autowired
	private IReminderRepository entityRepository;
    
	private Reminder entity1;

	private Reminder entity2;

    @Before
    public void setUp(){
    	this.entity1 = new Reminder();
    	this.entity1.setArchived(true);
    	this.entity1.setDate(LocalDateTime.parse("2018-01-14T18:05:21"));
    	this.entity2 = new Reminder();
    	this.entity2.setArchived(false);
    	this.entity2.setDate(LocalDateTime.parse("2018-01-15T19:06:22"));
    }
    
    @After
    public void tearDown() {
    	this.entityRepository.deleteAll();
    }
    
    @Test
	public void testList() {
		this.entityRepository.save(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list()).containsExactly(this.entity1, this.entity2);
	}
    
    @Test
   	public void testListEmpty() {
   		assertThat(this.entityService.list()).isEmpty();
   	}
    
    @Test
	public void testListArchived() {
		this.entityRepository.save(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list(true)).containsExactly(this.entity1);
	}
    
    @Test
	public void testListNotArchived() {
		this.entityRepository.save(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list(false)).containsExactly(this.entity2);
	}
    
    @Test
   	public void testListDate() {
    	LocalDateTime date = this.entity1.getDate();
    	this.entityRepository.save(Arrays.asList(this.entity1, this.entity2));
   		assertThat(this.entityService.list(date)).containsExactly(this.entity1);
   	}
    
    @Test
   	public void testGet() {
   		this.entityRepository.save(this.entity1);
   		assertThat(this.entityService.get(this.entity1.getId())).isEqualTo(this.entity1);
   	}
    
    @Test
   	public void testGetNotFound() {
   		assertThat(this.entityService.get("0")).isNull();
   	}
    
    @Test
   	public void testInsert() {
   		this.entityRepository.save(this.entity1);
   		assertThat(this.entityService.insert(this.entity1)).isEqualTo(this.entity1);
   	}
    
    @Test
   	public void testUpdate() {
    	this.entityRepository.save(this.entity1);
    	String newTitle = "modified";
    	this.entity1.setTitle(newTitle);
    	
    	Reminder updatedEntity = this.entityService.update(this.entity1);
   		assertThat(updatedEntity).isEqualTo(this.entity1);
    	assertThat(updatedEntity.getTitle()).isEqualTo(newTitle);
   	}
    
    @Test
   	public void testDelete() {
    	this.entityRepository.save(this.entity1);
    	this.entityService.delete(this.entity1.getId());
    	assertThat(this.entityService.get(this.entity1.getId())).isNull();
   	}
    
    @Test
   	public void testDeleteAll() {
    	this.entityRepository.save(this.entity1);
    	this.entityService.deleteAll();
    	assertThat(this.entityService.get(this.entity1.getId())).isNull();
    	assertThat(this.entityService.list()).isEmpty();
   	}

}
