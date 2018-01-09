package it.andreafailli.remindme.notifier.controllers;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.andreafailli.remindme.common.services.ReminderService;

@RestController
@RequestMapping(CronHandlerController.BASE_URL)
public class CronHandlerController {
	
public static final String BASE_URL = "/";
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(CronHandlerController.class);
	
	@Autowired
	private ReminderService reminderService;

	@RequestMapping
	public ResponseEntity<Void> handler() {
		LOGGER.entry();
		
		//TODO: list current reminders and send notifications
		ResponseEntity<Void> response = ResponseEntity.ok().build();
		
		LOGGER.exit(response);
		return response;
	}

}
