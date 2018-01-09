package it.andreafailli.remindme.common.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.repositories.IReminderRepository;

public class ReminderServiceTest {
	
	@InjectMocks
    ReminderService entityService;

    @Mock
    IReminderRepository entityRepository;
    
    Reminder entity1;

    Reminder entity2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.entity1 = new Reminder("1");
        this.entity2 = new Reminder("2");
    }
    
    @Test
	public void testList() {
		when(this.entityRepository.findAll()).thenReturn(Arrays.asList(this.entity1, this.entity2));
		assertThat(this.entityService.list()).containsExactly(this.entity1, this.entity2);
		verify(this.entityRepository).findAll();
	}
    
    @Test
   	public void testListEmpty() {
   		when(this.entityRepository.findAll()).thenReturn(new ArrayList<Reminder>());
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
   	public void testDelete() {
    	this.entityService.delete(this.entity1.getId());
    	verify(this.entityRepository).delete(this.entity1.getId());
   	}

}
