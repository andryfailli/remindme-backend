package it.andreafailli.remindme.common.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.andreafailli.remindme.common.models.Reminder;

@Repository
public interface IReminderRepository extends IEntityRepository<Reminder> {
	
	List<Reminder> findByArchived(boolean archived);
	
	List<Reminder> findByDate(LocalDateTime date);
}