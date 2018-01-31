package it.andreafailli.remindme.notifier.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
	
	public static class Data {
		
		private String title;
		
		@JsonProperty("click_action")
		private String clickAction;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getClickAction() {
			return clickAction;
		}

		public void setClickAction(String clickAction) {
			this.clickAction = clickAction;
		}
		
	}

	private String to;
	
	private Data data;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
}
