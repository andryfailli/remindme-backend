package it.andreafailli.remindme.api.controllers;

import java.net.URI;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.andreafailli.remindme.api.exceptions.BadRequestException;
import it.andreafailli.remindme.api.exceptions.EntityNotFoundException;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.UserService;

@RestController
@CrossOrigin
@RequestMapping(UserController.BASE_URL)
public class UserController {
	
	public static final String BASE_URL = "/api/user";
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ReminderController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<User> list() {
		LOGGER.entry();
		
		Iterable<User> users = this.userService.list();
		
		LOGGER.exit(users);
		return users;
	}
	
	@RequestMapping(path = "/me", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User me(Authentication authentication) {
		LOGGER.entry(authentication);
		
		User user = this.userService.get(authentication.getName());
		
		LOGGER.exit(user);
		return user;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User get(@PathVariable String id) {
		LOGGER.entry(id);
		
		User user = this.userService.get(id);
		if (user == null) throw new EntityNotFoundException(id);
		
		LOGGER.exit(user);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> insert(@RequestBody User user) {
		LOGGER.entry(user);
		
		if (user.getId() != null) throw new BadRequestException("When creating a new entity the id will be auto-generated.");
		user = this.userService.insert(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		ResponseEntity<User> response = ResponseEntity.created(location).<User>body(user);
		
		LOGGER.exit(response);
		return response;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public User update(@PathVariable String id, @RequestBody User user) {
		LOGGER.entry(user);
		
		if (user.getId() == null) throw new BadRequestException("When updating an entity, the id must be specified.");
		if (!user.getId().equals(id)) throw new BadRequestException("When updating an entity, the id of the entity must match the id specified in the URI.");
		user = this.userService.update(user);
		
		LOGGER.entry(user);
		return user;
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable String id) {
		LOGGER.entry(id);
		
		this.userService.delete(id);
		
		LOGGER.exit();
	}

}
