package it.andreafailli.remindme.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.repositories.ISubscriptionRepository;

@Service
public class SubscriptionService implements IEntityService<Subscription> {
	
	private ISubscriptionRepository userRepository;
	
	@Autowired
	public SubscriptionService(ISubscriptionRepository subscriptionRepository) {
		this.userRepository = subscriptionRepository;
	}

	public Iterable<Subscription> list() {
		return this.userRepository.findAll();
	}

	public Subscription get(String id) {
		return this.userRepository.findOne(id);
	}

	public Subscription insert(Subscription subscription) {
		return this.userRepository.save(subscription);
	}

	public Subscription update(Subscription subscription) {
		return this.userRepository.save(subscription);
	}
	
	public void delete(String id) {
		this.userRepository.delete(id);
	}

	public void deleteAll() {
		this.userRepository.deleteAll();
	}
	
}
