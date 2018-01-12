package it.andreafailli.remindme.api.controllers;

import java.net.URI;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.andreafailli.remindme.api.exceptions.BadRequestException;
import it.andreafailli.remindme.api.exceptions.EntityNotFoundException;
import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.services.ReminderService;

@RestController
@CrossOrigin
@RequestMapping(ReminderController.BASE_URL)
public class ReminderController {
	
	public static final String BASE_URL = "/api/reminder";
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ReminderController.class);
	
	@Autowired
	private ReminderService reminderService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Reminder> list() {
		LOGGER.entry();
		
		Iterable<Reminder> reminders = this.reminderService.list();
		
		LOGGER.exit(reminders);
		return reminders;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "archived", produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Reminder> listByArchived(@RequestParam boolean archived) {
		LOGGER.entry(archived);
		
		Iterable<Reminder> reminders = this.reminderService.list(archived);
		
		LOGGER.exit(reminders);
		return reminders;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Reminder get(@PathVariable String id) {
		LOGGER.entry(id);
		
		Reminder reminder = this.reminderService.get(id);
		if (reminder == null) throw new EntityNotFoundException(id);
		
		LOGGER.exit(reminder);
		return reminder;
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reminder> insert(@RequestBody Reminder reminder) {
		LOGGER.entry(reminder);
		
		if (reminder.getId() != null) throw new BadRequestException("When creating a new entity the id will be auto-generated.");
		reminder = this.reminderService.insert(reminder);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reminder.getId()).toUri();
		ResponseEntity<Reminder> response = ResponseEntity.created(location).<Reminder>body(reminder);
		
		LOGGER.exit(response);
		return response;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Reminder update(@PathVariable String id, @RequestBody Reminder reminder) {
		LOGGER.entry(reminder);
		
		if (reminder.getId() == null) throw new BadRequestException("When updating an entity, the id must be specified.");
		if (!reminder.getId().equals(id)) throw new BadRequestException("When updating an entity, the id of the entity must match the id specified in the URI.");
		reminder = this.reminderService.update(reminder);
		
		LOGGER.entry(reminder);
		return reminder;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable String id) {
		LOGGER.entry(id);
		
		this.reminderService.delete(id);
		
		LOGGER.exit();
	}

}
