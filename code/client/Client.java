package client;

import resources.MessageFacade;

public class Client {
	MessageFacade msg;
	String client_state;

	public Client(String client_state, MessageFacade msg) {
		this.client_state = client_state;
		this.msg = msg;
	}

	public void on() {
		System.out.println(client_state + " On");
	}

	public void off() {
		System.out.println(client_state + " Off");
	}

}
