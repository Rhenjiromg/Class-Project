package account;

public class TimeTestDriver {
	public static void main(String args[]) {
		Time time = new Time();
		
		System.out.println(time.getCreationDate());
		System.out.println(time.getCurrentDate());
		System.out.println(time.getCurrentTime());
		System.out.println(time.toString());
		
		try {
			Thread.sleep(5000); // sleep 5 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		// since the current time has a 5 seconds different
		// we can assume the current date also works.
		// using the same technique
		
		System.out.println(time.getCreationDate());
		System.out.println(time.getCurrentDate());
		System.out.println(time.getCurrentTime());
	}
}
