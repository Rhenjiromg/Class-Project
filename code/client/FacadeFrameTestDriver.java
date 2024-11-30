package client;

import shared.MessageFacade;

/* Driver to test facade */
public class FacadeFrameTestDriver {

	public static void main(String[] args) {
		MessageFacade msg = new MessageFacade("test response");
		Client clt = new Client("Test-Client", msg);

		FacadeFrame Message = new FacadeFrame(msg, clt);
		Message.sendMessage("test response.");
		Message.sentMessage();
	}

}
