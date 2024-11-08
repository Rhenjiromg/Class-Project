package  resources;
/* A facade class (temporary for testing)
 * 
 * named FacadeFrame(Message) with 1 attribute Message and 1 method
 * aside from constructor that print out the content of the message passed,
 * AND also returns a message object that contains the data string
 * "test response" with enum type "Log in"
 * 
 * */

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


