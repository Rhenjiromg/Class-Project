package client;

import resources.MessageFacade;

public class FacadeFrame {
	MessageFacade msg;
	Client clt;

	public FacadeFrame(MessageFacade msg, Client clt) {
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
