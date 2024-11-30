<<<<<<< HEAD
package shared;
=======
<<<<<<< HEAD:code/resources/MessageFacade.java
package resources;
=======
package  shared;
>>>>>>> a9b181cafc03fa0189c27d6fdd36a38ce4333388
/* A facade class (temporary for testing)
 * 
 * named FacadeFrame(Message) with 1 attribute Message and 1 method
 * aside from constructor that print out the content of the message passed,
 * AND also returns a message object that contains the data string
 * "test response" with enum type "Log in"
 * 
 * */
<<<<<<< HEAD
=======
>>>>>>> 2e524382b4d4bc83681bc408d6ad9ee940df57f4:code/shared/MessageFacade.java
>>>>>>> a9b181cafc03fa0189c27d6fdd36a38ce4333388

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
