package it.andreafailli.remindme.common.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
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
    
    List<Reminder> entities;
    
    Reminder entity;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.entity = new Reminder("1");
        this.entities = Arrays.asList(this.entity, new Reminder("2"));
    }
    
    @Test
	public void testList() {
		when(this.entityRepository.findAll()).thenReturn(this.entities);
		assertEquals(this.entities, IteratorUtils.toList(this.entityService.list().iterator()));
	}
    
    @Test
   	public void testGet() {
   		when(this.entityRepository.findOne(this.entity.getId())).thenReturn(this.entity);
   		assertEquals(this.entity, this.entityService.get(this.entity.getId()));
   	}
    
    @Test
   	public void testInsert() {
   		when(this.entityRepository.save(this.entity)).thenReturn(this.entity);
   		assertEquals(this.entity, this.entityService.insert(this.entity));
   	}
    
    @Test
   	public void testUpdate() {
   		when(this.entityRepository.save(this.entity)).thenReturn(this.entity);
   		assertEquals(this.entity, this.entityService.update(this.entity));
   	}
    
    @Test
   	public void testDelete() {
    	this.entityService.delete(this.entity.getId());
   	}

}
