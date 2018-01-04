package it.andreafailli.remindme.common.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import it.andreafailli.remindme.common.models.BaseEntity;

@NoRepositoryBean
public interface IEntityRepository<T extends BaseEntity> extends CrudRepository<T, String> {

}