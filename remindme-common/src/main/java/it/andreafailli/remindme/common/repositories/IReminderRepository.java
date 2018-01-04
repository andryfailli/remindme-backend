package it.andreafailli.remindme.common.repositories;

import org.springframework.stereotype.Repository;

import it.andreafailli.remindme.common.models.Reminder;

@Repository
public interface IReminderRepository extends IEntityRepository<Reminder> {

}