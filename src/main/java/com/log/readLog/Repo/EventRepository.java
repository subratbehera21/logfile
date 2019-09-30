package com.log.readLog.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.log.readLog.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, String>{

}
