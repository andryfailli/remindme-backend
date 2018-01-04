package it.andreafailli.remindme.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.repositories.IUserRepository;

@Service
public class UserService implements IEntityService<User> {
	
	private IUserRepository userRepository;
	
	@Autowired
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Iterable<User> list() {
		return this.userRepository.findAll();
	}

	public User get(String id) {
		return this.userRepository.findOne(id);
	}

	public User insert(User user) {
		return this.userRepository.save(user);
	}

	public User update(User user) {
		return this.userRepository.save(user);
	}
	
	public void delete(String id) {
		this.userRepository.delete(id);
	}

}
