package it.andreafailli.remindme.api.controllers;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.andreafailli.remindme.api.exceptions.BadRequestException;
import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.services.SubscriptionService;

@RestController
@CrossOrigin
@RequestMapping(SubscriptionController.BASE_URL)
public class SubscriptionController {
	
	public static final String BASE_URL = "/api/subscription";
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SubscriptionController.class);
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@RequestMapping(path = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Subscription save(@PathVariable String id, @RequestBody Subscription subscription) {
		LOGGER.entry(subscription);
		
		if (subscription.getId() == null) throw new BadRequestException("When saving a subscription, the id must be specified.");
		if (!subscription.getId().equals(id)) throw new BadRequestException("When saving a subscription, the id of the entity must match the id specified in the URI.");
		subscription = this.subscriptionService.save(subscription);
		
		LOGGER.entry(subscription);
		return subscription;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable String id) {
		LOGGER.entry(id);
		
		this.subscriptionService.delete(id);
		
		LOGGER.exit();
	}

}
