package com.log.readLog.EventDAO.DAOImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.log.readLog.EventDAO.EventDAO;
import com.log.readLog.Repo.EventRepository;
import com.log.readLog.model.Event;

@Service
public class EventDAOImpl implements EventDAO{
	
	private static final Logger log = LoggerFactory.getLogger(EventDAOImpl.class);
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public void saveEvent(Event event) {
		log.info("Storing Event :" + event.getEventId());
		eventRepository.save(event);
	}

	@Override
	public Event getEventById(String id) {
		Optional<Event> event =  eventRepository.findById(id);
		return event.get();
	}
	
}
