package com.log.readLog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logevent")
public class Event {
	
	@Id
	private String eventId;
	
	private long duration;
	
	private String type;
	
	private String host;
	
	private boolean alert;
	
	public Event() {
	}
	public Event(String eventId, long duration, String type, String host, boolean alert) {
		this.eventId = eventId;
		this.duration = duration;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
}
