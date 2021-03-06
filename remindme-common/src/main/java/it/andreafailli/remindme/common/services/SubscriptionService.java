package it.andreafailli.remindme.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.andreafailli.remindme.common.models.Subscription;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.repositories.ISubscriptionRepository;

@Service
public class SubscriptionService implements IEntityService<Subscription> {
	
	private ISubscriptionRepository subscriptionRepository;
	
	@Autowired
	public SubscriptionService(ISubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	public Iterable<Subscription> list() {
		return this.subscriptionRepository.findAll();
	}
	
	public Iterable<Subscription> list(User user) {
		return this.subscriptionRepository.findByUserId(user.getId());
	}

	public Subscription get(String id) {
		return this.subscriptionRepository.findOne(id);
	}

	public Subscription insert(Subscription subscription) {
		return this.save(subscription);
	}

	public Subscription update(Subscription subscription) {
		return this.save(subscription);
	}
	
	public Subscription save(Subscription subscription) {
		return this.subscriptionRepository.save(subscription);
	}
	
	public void delete(String id) {
		this.subscriptionRepository.delete(id);
	}

	public void deleteAll() {
		this.subscriptionRepository.deleteAll();
	}
	
}
