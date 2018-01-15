package it.andreafailli.remindme.common.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.repositories.IReminderRepository;

@Service
public class ReminderService implements IEntityService<Reminder> {
	
	private IReminderRepository reminderRepository;
	
	@Autowired
	public ReminderService(IReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	public Iterable<Reminder> list() {
		return this.reminderRepository.findAll();
	}
	
	public Iterable<Reminder> list(boolean archived) {
		return this.reminderRepository.findByArchived(archived);
	}
	
	public Iterable<Reminder> list(LocalDateTime date) {
		return this.reminderRepository.findByDate(date.truncatedTo(ChronoUnit.MINUTES));
	}

	public Reminder get(String id) {
		return this.reminderRepository.findOne(id);
	}

	public Reminder insert(Reminder reminder) {
		return this.reminderRepository.save(reminder);
	}

	public Reminder update(Reminder reminder) {
		return this.reminderRepository.save(reminder);
	}
	
	public void delete(String id) {
		this.reminderRepository.delete(id);
	}
	
	public void deleteAll() {
		this.reminderRepository.deleteAll();
	}

}
