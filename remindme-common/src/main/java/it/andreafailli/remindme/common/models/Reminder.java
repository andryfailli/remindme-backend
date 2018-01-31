package it.andreafailli.remindme.common.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reminder extends BaseEntity {

	private String title;
	
	private LocalDateTime date;
	
	private User user;
	
	private boolean archived;
	
	public Reminder() {}
	
	public Reminder(String id) {
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date.truncatedTo(ChronoUnit.MINUTES);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
}