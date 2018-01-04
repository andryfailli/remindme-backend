package it.andreafailli.remindme.common.models;

import java.util.Date;

public class Reminder extends BaseEntity {

	private String title;
	
	private Date date;
	
	private User user;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}