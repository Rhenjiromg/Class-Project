package resources;

import java.util.Date;

public class Time {
	private Date date;
	private final String[] dateArray;
	
	public Time() {
		date = new Date();
		dateArray = (date.toString()).split(" ");
	}
	
	
	public String getCreationDate() {
		return dateArray[1] + " " + dateArray[2] + " " + dateArray[5];
	}
	

	public String getCurrentDate() {
		String[] dateArray = (new Date().toString()).split(" ");
		return dateArray[1] + " " + dateArray[2] + " " + dateArray[5];
	}
	
	
	public String getCurrentTime() {
		return ((new Date().toString()).split(" "))[3];
	}
	
	
	public String toString() {
		// NOTE: returns the Creation date not current
		return date.toString();
	}
}
