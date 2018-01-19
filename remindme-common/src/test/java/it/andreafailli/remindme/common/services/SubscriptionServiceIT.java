package it.andreafailli.remindme.common.services;

import static org.assertj.core.api.Assertions.assertThat;

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
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.repositories.ISubscriptionRepository;
import it.andreafailli.remindme.testing.IntegrationTestCategory;

@RunWith(SpringRunner.class)
@Category(IntegrationTestCategory.class)
@SpringBootTest(classes = RemindMeCommonTestApplication.class)
@ActiveProfiles(Profiles.TEST)
public class SubscriptionServiceIT {
	
	@Autowired
    private SubscriptionService entityService;

	@Autowired
	private ISubscriptionRepository entityRepository;
    
	private Subscription entity1;

	private Subscription entity2;

    @Before
    public void setUp(){
    	this.entity1 = new Subscription();
    	this.entity2 = new Subscription();
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
	public void testListUser() {
    	User user1 = new User("1");
    	User user2 = new User("2");
    	this.entity1.setUserId(user1.getId());
    	this.entity2.setUserId(user2.getId());
		this.entityRepository.save(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list(user1)).containsExactly(this.entity1);
	}
    
    @Test
   	public void testListUserEmpty() {
    	User user1 = new User("1");
   		assertThat(this.entityService.list(user1)).isEmpty();
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
    	String newValue = "modified";
    	this.entity1.setUserId(newValue);
    	
    	Subscription updatedEntity = this.entityService.update(this.entity1);
   		assertThat(updatedEntity).isEqualTo(this.entity1);
    	assertThat(updatedEntity.getUserId()).isEqualTo(newValue);
   	}
    
    @Test
   	public void testSaveInsert() {
   		this.entityRepository.save(this.entity1);
   		assertThat(this.entityService.save(this.entity1)).isEqualTo(this.entity1);
   	}
    
    @Test
   	public void testSaveUpdate() {
    	this.entityRepository.save(this.entity1);
    	String newValue = "modified";
    	this.entity1.setUserId(newValue);
    	
    	Subscription updatedEntity = this.entityService.save(this.entity1);
   		assertThat(updatedEntity).isEqualTo(this.entity1);
    	assertThat(updatedEntity.getUserId()).isEqualTo(newValue);
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
