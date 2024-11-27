package resources;

public class MessageFacade {
	String message = "test response.";

	enum Message_type {
		Log_in,
		Log_out
	}

	public MessageFacade(String message) {
		this.message = message;
	}

	public void sending() {
		System.out.println("Message: " + message + " sending.");
		System.out.println("ENUM STATE: " + Message_type.Log_in);
	}

	public void sent() {
		System.out.println("Message: " + message + " sent.");
		System.out.println("ENUM STATE: " + Message_type.Log_out);
	}
}
