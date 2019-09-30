package com.log.readLog.EventDAO;

import com.log.readLog.model.Event;

public interface EventDAO {

	void saveEvent(Event event);
	
	Event getEventById(String id);

}
