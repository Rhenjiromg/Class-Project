package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import resources.Message;
import resources.Port;

public class Server {
	protected Queue<Message> incoming = new ConcurrentLinkedQueue<>();
	protected Queue<Message> outgoing = new ConcurrentLinkedQueue<>();
	
	public static void main(String[] args) {
		System.out.println("Server active");
		
		// run serverListner, Responder and processor 
		// as threads at the same time for parallel execution
		Server server = new Server();
		(new Thread(server.new ServerListener())).start();
		(new Thread(server.new ServerSender())).start();
		(new Thread(server.new ServerProcessor())).start();
	}
	
	// purpose: put message from client in incoming queue.
	public class ServerListener implements Runnable {
		@Override
		public void run() {
			System.out.println("Listner thread active");
			try (ServerSocket serverListener = new ServerSocket(new Port().getPort())) {
				
				while (true) {
					Socket client = serverListener.accept();
					System.out.println("client connection accepted" + client);
					// get the serialized message object
					ObjectInputStream in = new ObjectInputStream(client.getInputStream());
					// add the message object to the queue
					incoming.add((Message) in.readObject());
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	// purpose: send message to clients in order from outgoing queue
	public class ServerSender implements Runnable {
		
		@Override
		public void run() {
			System.out.println("Sender thread active");
			// get message from outgoing
			Message message = outgoing.poll();
			if (message != null) {				
				try (Socket clientSocket = new Socket(message.getSender(), new Port().getPort())) {
					System.out.println("sending message to " + clientSocket);
					ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
					// send the message to the client
					out.writeObject(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// purpose: get message from incoming queue, process them
	// add them to the outgoing queue
	public class ServerProcessor implements Runnable {

		@Override
		public void run() {
			// get the message
			// while incoming queue has items
			while (!incoming.isEmpty()){
				Message message = incoming.poll();
				
				// process the message in a separate thread
				
				// add a new message for client to the
				// outgoing queue
				if (message != null) {				
					outgoing.add(message);
				}
				// put this in thread as well ^^
			}
		}
	}
}