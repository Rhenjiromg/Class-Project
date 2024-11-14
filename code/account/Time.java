package account;

import java.util.Date;

public class Time {
	private Date date;
	
	Time() {
		date = new Date();
	}
	
	public String getCreationDate() {
		String[] arr = (date.toString()).split(" ");
		return arr[1] + " " + arr[2] + " " + arr[5];
	}
	
	public String getCurrentDate() {
		String[] arr = (new Date().toString()).split(" ");
		return arr[1] + " " + arr[2] + " " + arr[5];
	}
	
	public String getCurrentTime() {
		String[] arr = (new Date().toString()).split(" ");
		return arr[3];
	}
	
	public String toString() {
		return date.toString();
	}
}
