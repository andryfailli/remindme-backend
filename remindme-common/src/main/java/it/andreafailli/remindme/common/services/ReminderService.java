package it.andreafailli.remindme.common.services;

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

}
