package server;

import java.io.*;
import java.net.*;

import java.util.ArrayDeque;
import java.util.Queue;

import shared.*;


public class Server {
	private int port;
	
	public Server() {
		this.port = 31415;
	}
	
	public Server(int p) {
		this.port = p;
	}
	
	public void start() { //the reason why this look like this is because i made threads first then mod for client/server
		new Thread(new InnitSession()).start();
	}
	
	private class InnitSession implements Runnable {

		
		@Override
        public void run() {
			
			try (ServerSocket serverSocket = new ServerSocket(port)){
            while(!serverSocket.isClosed()){
				Socket client = serverSocket.accept();
				new Thread(new Session(client)).start();
				}
			} catch (IOException e) {

			}
		}
		
		private class Session implements Runnable {
			private Socket soc;
			private ObjectOutputStream out = null;
			private ObjectInputStream in = null;
			private ServerFacade serverFacade = new ServerFacade();


			//session bounded queue
			private Queue<Message> outbound = new ArrayDeque<>();
			private Queue<Message> inbound = new ArrayDeque<>();
			private synchronized void addQueue(Queue<Message> queue, Message message) {
		        queue.add(message);
		        queue.notify();
		    }

			private synchronized Message popQueue(Queue<Message> queue) {
		        return queue.poll();
		    }

			public Session(Socket s){
				this.soc = s;
				try {
					System.out.println("Server Session innit'd");
					this.out = new ObjectOutputStream(soc.getOutputStream());
					this.in = new ObjectInputStream(soc.getInputStream());
				} catch (EOFException e) { 
					System.out.println("Connection closed by the client: " + e.getMessage()); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				System.out.println("Connection started by the client: ");
				new Thread(new ListenSession()).start();
				new Thread(new ProcessSession()).start();
				new Thread(new SenderSession()).start();
			}
			
			//Start: listen for session
			private class ListenSession implements Runnable {
				@Override
				public void run() {
					try {
						//keep getting end of stream error
						while(!soc.isClosed()) {
							//System.out.println("Listening loop");
								Message bufferM = (Message) in.readObject();
								System.out.println("Hear a message!");
								new Thread(new Listen(bufferM)).start();
						}
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				
				//listen...
				private class Listen implements Runnable {
					private Message msg;
					
					public Listen(Message m) {
						this.msg = m;
					}
					
					@Override
					public void run() {
						synchronized (inbound) {
							addQueue(inbound, msg);
						}
					}
				} //end scope of Listen
			} //end scope of sessionListen
			
			//Start: process for session
			private class ProcessSession implements Runnable {
				@Override
				public void run() {
					while(true) {
						Message buffer;
						synchronized (inbound) { 
							while (inbound.isEmpty()) { 
								try {
									inbound.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								} // Wait until there's something in the queue } buffer = popQueue(outbound); // Retrieve the message }
							}
							buffer = popQueue(inbound);
							System.out.println("progress loop");
							new Thread(new Process(buffer)).start();
						}
					}	
				}
				
				private class Process implements Runnable {
					//TODO: mount facade here
					private Message msg;
					
					public Process(Message m) {
						this.msg = m;
					}
					//for msg coming in that doesnt fit the designed case, it would simply be drop
					@Override
					public void run() {
						Message buffer;
						switch (msg.getType()) {
							case LOGOUT:
								
								// If logout request is sent, end the session.
								
								sendMessage(MessageType.SUCCESS);
								break;
						    case LOGIN: // login
						    	// if user has this account let them in
						    	sendMessage(serverFacade.login(msg));
						    	break;
                
						    case ACCOUNT_INFO:
						    	sendMessage(serverFacade.getInfo(msg));
						    	
						        break;
						    case DEPOSIT:
						    	//          returns Message                account ID           amount
						    	sendMessage(serverFacade.depositAmount(msg.getMessage()[0], msg.getMessage()[1]));
						    	break;
						    case WITHDRAW:
						    	//          returns Message                account ID           amount
						    	sendMessage(serverFacade.withdrawAmount(msg.getMessage()[0], msg.getMessage()[1]));
						    	break;
						    case TRANSFER:
					            //          returns Message                 account ID           target account ID    amount to transfer
						    	sendMessage(serverFacade.transferAmount(msg.getMessage()[0], msg.getMessage()[1], msg.getMessage()[2]));
						    	break;
							case TRANSACTION_HISTORY:
								
								//          returns Message                     account ID
								sendMessage(serverFacade.transactionHistory(msg.getMessage()[0]));
								break;
							case ADD_USER:
								//                       account ID           super user ID        user name            password
								sendMessage(serverFacade.addUser(msg.getMessage()[0], msg.getMessage()[1], msg.getMessage()[2], msg.getMessage()[3]));
								break;
							case CREATE_ACCOUNT:
								
                                //		                                   account ID           super user ID
								sendMessage(serverFacade.createAccount(msg.getMessage()[0], msg.getMessage()[1]));
								break;
							case DEACTIVATE_ACCOUNT:
								
		                        //                                             account ID           super user ID
								sendMessage(serverFacade.deactivateAccount(msg.getMessage()[0], msg.getMessage()[1]));
								break;
						    default:
						    	sendMessage(MessageType.ERROR);
						    	// drop message by default
						    	break;
						}
					}
					
					// way to send message to client
					private void sendMessage(MessageType mt) {
						synchronized (outbound) {
							addQueue(outbound, new Message(mt));
						}
					}
					
					
					private void sendMessage(Message m) {
						synchronized (outbound) {
							addQueue(outbound, m);
						}
					}
				}//end scope process
			} //end scope processSession
			
			//Start: session sender
			private class SenderSession implements Runnable {

				@Override
				public void run() {
					System.out.println("start sender thread.");
					try {
						while(true) {
							Message buffer;
							synchronized (outbound) { 
								while (outbound.isEmpty()) { 
									try {
										outbound.wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									} // Wait until there's something in the queue } buffer = popQueue(outbound); // Retrieve the message }
								}
							}
								buffer = popQueue(outbound);
								System.out.println("Sending loop");
								out.writeObject(buffer);
								out.flush();
								System.out.println("Sent message");
						} //end while out
					} catch (IOException e) {

					}
					
				}
				
			}//end session sender scope
			
		} //end scope of session thread
		
    }//end scope of innitport thread
}
