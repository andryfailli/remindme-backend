package it.andreafailli.remindme.common.models;

public class Subscription extends BaseEntity {

	private String userId;
	
	public Subscription() {}
	
	public Subscription(String id) {
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
