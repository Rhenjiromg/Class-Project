package client;

import resources.Message_Facade;

public class FacadeFrame {
	Message_Facade msg;
	Client clt;

	public FacadeFrame(Message_Facade msg, Client clt) {
		this.msg = msg;
		this.clt = clt;
	}

	public void sendMessage(String message) {
		System.out.println("Preparing to send a message...." + "\n");
		clt.on();
		msg.sending();
	}

	public void sentMessage() {
		System.out.println();
		System.out.println("Message has been sent...." + "\n");
		clt.off();
		msg.sent();
	}

}
