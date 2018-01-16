package it.andreafailli.remindme.common.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.andreafailli.remindme.common.models.Subscription;

@Repository
public interface ISubscriptionRepository extends IEntityRepository<Subscription> {
	
	List<Subscription> findByUserId(String userId);

}