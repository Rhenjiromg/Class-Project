package client;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

import shared.*;

public class Client {
	// test with local host, will have to change if i test with vm. This field must
	// be known out of code
	private int port;

	public Client() {
		this.port = 31415;
	}

	public Client(int p) {
		this.port = p;
	}

	// NOTE: same with server, this first thread can be made into the start method
	// for client/server
	public void start() { // the reason why this look like this is because i made threads first then mod
							// for client/server
		new Thread(new InnitPort()).start();
	}

	private class InnitPort implements Runnable {

		@Override
		public void run() {
			Socket socket = null;
			try {
				socket = new Socket("localhost", port);
				new Thread(new Session(socket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private class Session implements Runnable {
			private GUI gui;
			private Socket soc;
			private ObjectOutputStream out;
			private ObjectInputStream in;

			
			//queues here so its socket bounded
			private Queue<Message> outbound = new ArrayDeque<>();
			private Queue<Message> inbound = new ArrayDeque<>();
			private synchronized void addQueue(Queue<Message> queue, Message message) {
				queue.add(message);
				queue.notify();
			}

			private synchronized Message popQueue(Queue<Message> queue) {
				return queue.poll();
			}

			public Session(Socket s) {
				this.soc = s;
				//pass queue to gui by reference
				gui = new GUI(outbound);
				
				try {
					this.out = new ObjectOutputStream(soc.getOutputStream());
					this.in = new ObjectInputStream(soc.getInputStream());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void run() {
				// this is a client only feature
				Message msg = gui.login();
				synchronized (outbound) {
					addQueue(outbound, msg);
				}

				Thread listenThread = new Thread(new ListenSession());
				Thread processThread = new Thread(new ProcessSession());
				Thread senderThread = new Thread(new SenderSession());

				listenThread.start();
				processThread.start();
				senderThread.start();
				// Use join to wait for all threads to finish
				try {
					listenThread.join();
					processThread.join();
					senderThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("error: threads exit prematurely");
			}


			// Start: listen for session

			private class ListenSession implements Runnable {
				@Override
				public void run() {
					try {
						System.out.println("start listen thread.");
						while (!soc.isClosed()) {
							Message bufferM = (Message) in.readObject();
							new Thread(new Listen(bufferM)).start();
						}
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}

				}

				// listen...
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
				} // end scope of Listen
			} // end scope of sessionListen

			// Start: process for session
			private class ProcessSession implements Runnable {

				@Override
				public void run() {
					System.out.println("start process thread.");
					while (true) {
						Message buffer;
						synchronized (inbound) {
							while (inbound.isEmpty()) {
								try {
									inbound.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								} // Wait until there's something in the queue } buffer = popQueue(outbound); //
									// Retrieve the message }
							}
							buffer = popQueue(inbound);
							new Thread(new Process(buffer)).start();
						}
					}
				}
				
				private class Process implements Runnable {
					private Message msg;

					public Process(Message m) {
						this.msg = m;
					}

					// for msg coming in that doesn't fit the designed case, it would simply be drop
					@Override
					public void run() {
						String[] buffer;
						ArrayList<String> bList = new ArrayList<String>();
						Operator opbuffer;
						Account accbuffer;
						switch (msg.getType()) {
						//this way only when log in is success then we show display?
					    case LOGIN:
							
							buffer = msg.getMessage();
							if (buffer[1].charAt(1) == '0'){ //user
								
								for (int i = 4; i < buffer.length; i++){
									bList.add(buffer[i]);
								}
								opbuffer = new User(buffer[0], buffer[1], buffer[2], buffer[3], bList);
								gui.userDisplay((User) opbuffer);
							} else {
								opbuffer = new SuperUser(buffer[0], buffer[1], buffer[2], buffer[3]);
								gui.superUserDisplay((SuperUser) opbuffer);
							}
					        break;
						//try to run this, see if it can update the acc on gui while still display
					        
					    case ACCOUNT_INFO:
					    	buffer = msg.getMessage();
					    	if (buffer[0].charAt(1) == '0'){  //saving
					    	    accbuffer = new SavingAccount(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]);
					    	} else {
					    	    accbuffer = new CheckingAccount(buffer[0], buffer[1], buffer[2], buffer[3]);
					    	}
							gui.updateAccount(accbuffer);
							break;
					    case UPDATEERROR:
					    	buffer = msg.getMessage();
					    	for (int i = 4; i < buffer.length; i++){
								bList.add(buffer[i]);
							}
					    	opbuffer = new User(buffer[0], buffer[1], buffer[2], buffer[3], bList);
					    	gui.updateUser((User) opbuffer);
					    	break;
					    case DEPOSIT:
					    	buffer = msg.getMessage();
					    	if (buffer[0].charAt(1) == '0'){  //saving
					    	    accbuffer = new SavingAccount(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]);
					    	} else {
					    	    accbuffer = new CheckingAccount(buffer[0], buffer[1], buffer[2], buffer[3]);
					    	}
							gui.updateAccount(accbuffer);
							break;
						//TODO:ADD TO THIS
					    case WITHDRAW:
					    	buffer = msg.getMessage();
					    	if (buffer[0].charAt(1) == '0'){  //saving
					    	    accbuffer = new SavingAccount(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]);
					    	} else {
					    	    accbuffer = new CheckingAccount(buffer[0], buffer[1], buffer[2], buffer[3]);
					    	}
							gui.updateAccount(accbuffer);
							break;
					    case TRANSFER:
					    	buffer = msg.getMessage();
					    	if (buffer[0].charAt(1) == '0'){  //saving
					    	    accbuffer = new SavingAccount(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]);
					    	} else {
					    	    accbuffer = new CheckingAccount(buffer[0], buffer[1], buffer[2], buffer[3]);
					    	}
							gui.updateAccount(accbuffer);
							break;
					    case TRANSACTION_HISTORY:
					    	buffer = msg.getMessage();
					    	if (buffer[0].charAt(1) == '0'){  //saving
					    	    accbuffer = new SavingAccount(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]);
					    	} else {
					    	    accbuffer = new CheckingAccount(buffer[0], buffer[1], buffer[2], buffer[3]);
					    	}
							gui.updateAccount(accbuffer);
							break;
						//TODO: su events
						case ADD_USER:
						
							break;
						case CREATE_ACCOUNT:
							
							break;
						case DEACTIVATE_ACCOUNT:
							
							break;
						case ERROR:
					    	
					    	break; 	
					    default:
					    	break;
						}
					}

					// Check to establish basic handshake, once that is done we got a session and
					// constantly send txt until logout bool is true
					
					
					//
					//
					// public void text() {
					// if (msg.getMessage().equals("SUCCESS")) {
					// System.out.println("Server response: " + msg.getMessage());
					// }
					// }
					//
					// public void logout() {
					// if (msg.getType().equals(MessageType.LOGOUT)) {
					// Logout = true;
					// try {
					// if (in != null)
					// in.close();
					//
					// if (out != null)
					// out.close();
					//
					// if (soc != null && !soc.isClosed())
					// soc.close();
					// }
					// catch (IOException e) {
					// e.printStackTrace(); // Handle exceptions properly } }
					// }
					// }
					// }
				} // end scope process
			} // end scope processSession

			// Start: session sender
			private class SenderSession implements Runnable {

				@Override
				public void run() {
					System.out.println("start sender thread.");
					try {
						while (true) { // Main loop to keep the thread alive
							Message buffer;

							synchronized (outbound) {
								// Wait for messages in the queue
								while (outbound.isEmpty()) {
									try {
										outbound.wait(); // Wait until notified
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								buffer = popQueue(outbound); // Retrieve the next message to send
							} // End synchronized block

							// Send the message outside the synchronized block
							System.out.println("event horizon: writer");
							out.writeObject(buffer);
							out.flush();
							System.out.println("Sent message");
						}
					} catch (IOException e) {
						// Handle and log IOException properly
						e.printStackTrace();
						System.out.println("Sender thread terminated due to IOException: " + e.getMessage());
					}
				}
			}

		}// end scope of session thread

	} // end scope of innitport thread
}
