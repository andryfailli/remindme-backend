package it.andreafailli.remindme.common.services;

import org.springframework.stereotype.Service;

import it.andreafailli.remindme.common.models.BaseEntity;

@Service
public interface IEntityService<T extends BaseEntity> {
	
	public Iterable<T> list();

	public T get(String id);

	public T insert(T entity);

	public T update(T entity);
	
	public void delete(String id);
	
	public void deleteAll();

}
