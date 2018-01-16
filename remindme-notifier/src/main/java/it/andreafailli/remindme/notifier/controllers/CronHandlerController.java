package it.andreafailli.remindme.notifier.controllers;

import java.time.LocalDateTime;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.andreafailli.remindme.common.models.Reminder;
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.services.ReminderService;
import it.andreafailli.remindme.common.services.SubscriptionService;
import it.andreafailli.remindme.notifier.models.Notification;
import it.andreafailli.remindme.notifier.services.NotificationService;

@RestController
@RequestMapping(CronHandlerController.BASE_URL)
public class CronHandlerController {
	
public static final String BASE_URL = "/";
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(CronHandlerController.class);
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private SubscriptionService subscriptionService;

	@Value("${remindme.frontend.url}")
	private String remindmeFrontendUrl;
	
	@RequestMapping
	public ResponseEntity<Void> handler() {
		LOGGER.entry();
		
		LocalDateTime now = LocalDateTime.now();
		
		Iterable<Reminder> reminders = this.reminderService.list(now);
		for (Reminder reminder : reminders) {
			this.sendNotification(reminder);
		}
		
		ResponseEntity<Void> response = ResponseEntity.ok().build();
		
		LOGGER.exit(response);
		return response;
	}
	
	private void sendNotification(Reminder reminder) {
		Iterable<Subscription> subscriptions = this.subscriptionService.list(reminder.getUser());
		for (Subscription subscription : subscriptions) {
			Notification notification = new Notification();
			Notification.Data notificationData = new Notification.Data();
			notification.setTo(subscription.getId());
			notification.setData(notificationData);
			notificationData.setClickAction(this.remindmeFrontendUrl);
			notificationData.setTitle(reminder.getTitle());
			
			this.notificationService.send(notification);
		}
	}

}
