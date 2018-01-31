package it.andreafailli.remindme.common.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.repositories.IReminderRepository;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class ReminderServiceTest {
	
	@InjectMocks
	private ReminderService entityService;

    @Mock
    private IReminderRepository entityRepository;
    
    private Reminder entity1;

    private Reminder entity2;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        
        this.entity1 = new Reminder("1");
        this.entity1.setArchived(true);
        this.entity1.setDate(LocalDateTime.parse("2018-01-14T18:05:21"));
        this.entity2 = new Reminder("2");
        this.entity2.setArchived(false);
        this.entity2.setDate(LocalDateTime.parse("2018-01-15T19:06:22"));
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
	public void testListArchived() {
		when(this.entityRepository.findByArchived(true)).thenReturn(Arrays.asList(this.entity1));
		assertThat(this.entityService.list(true)).containsExactly(this.entity1);
		verify(this.entityRepository).findByArchived(true);
	}
    
    @Test
   	public void testListDate() {
    	LocalDateTime date = this.entity1.getDate();
   		when(this.entityRepository.findByDate(date)).thenReturn(Arrays.asList(this.entity1));
   		assertThat(this.entityService.list(date)).containsExactly(this.entity1);
   		verify(this.entityRepository).findByDate(date);
   	}
    
    @Test
	public void testListNotArchived() {
		when(this.entityRepository.findByArchived(false)).thenReturn(Arrays.asList(this.entity2));
		assertThat(this.entityService.list(false)).containsExactly(this.entity2);
		verify(this.entityRepository).findByArchived(false);
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
    
    @Test
   	public void testDeleteAll() {
    	this.entityService.deleteAll();
    	verify(this.entityRepository).deleteAll();
   	}

}
