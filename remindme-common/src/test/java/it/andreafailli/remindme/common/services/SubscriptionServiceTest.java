package it.andreafailli.remindme.common.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.repositories.ISubscriptionRepository;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class SubscriptionServiceTest {
	
	@InjectMocks
    SubscriptionService entityService;

    @Mock
    ISubscriptionRepository entityRepository;
    
    Subscription entity1;
    
    Subscription entity2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.entity1 = new Subscription("1");
        this.entity2 = new Subscription("2");
    }
    
    @Test
	public void testList() {
		when(this.entityRepository.findAll()).thenReturn(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list()).containsExactly(this.entity1, this.entity2);
		verify(this.entityRepository).findAll();
	}
    
    @Test
   	public void testListEmpty() {
   		when(this.entityRepository.findAll()).thenReturn(new ArrayList<Subscription>());
   		assertThat(this.entityService.list()).isEmpty();
   		verify(this.entityRepository).findAll();
   	}
    
    @Test
   	public void testGet() {
   		when(this.entityRepository.findOne(this.entity1.getId())).thenReturn(this.entity1);
   		assertThat(this.entityService.get(this.entity1.getId())).isEqualTo(this.entity1);
   		verify(this.entityRepository).findOne(this.entity1.getId());
   	}
    
    @Test
   	public void testGetNotFound() {
   		assertThat(this.entityService.get(this.entity2.getId())).isNull();
   		verify(this.entityRepository).findOne(this.entity2.getId());
   	}
    
    @Test
   	public void testInsert() {
   		when(this.entityRepository.save(this.entity1)).thenReturn(this.entity1);
   		assertThat(this.entityService.insert(this.entity1)).isEqualTo(this.entity1);
   		verify(this.entityRepository).save(this.entity1);
   	}
    
    @Test
   	public void testUpdate() {
   		when(this.entityRepository.save(this.entity1)).thenReturn(this.entity1);
   		assertThat(this.entityService.update(this.entity1)).isEqualTo(this.entity1);
   		verify(this.entityRepository).save(this.entity1);
   	}
    
    @Test
   	public void testSave() {
   		when(this.entityRepository.save(this.entity1)).thenReturn(this.entity1);
   		assertThat(this.entityService.save(this.entity1)).isEqualTo(this.entity1);
   		verify(this.entityRepository).save(this.entity1);
   	}
    
    @Test
   	public void testDelete() {
    	this.entityService.delete(this.entity1.getId());
    	verify(this.entityRepository).delete(this.entity1.getId());
   	}
    
    @Test
   	public void testDeleteAll() {
    	this.entityService.deleteAll();
    	verify(this.entityRepository).deleteAll();
   	}

}
