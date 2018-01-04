package it.andreafailli.remindme.common.repositories;

import org.springframework.stereotype.Repository;

import it.andreafailli.remindme.common.models.User;

@Repository
public interface IUserRepository extends IEntityRepository<User> {

}