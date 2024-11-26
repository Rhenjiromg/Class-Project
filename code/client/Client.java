package client;

import java.io.*;
import java.net.*;
import resources.Message;
import resources.MessageType;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;


public class Client {
 //test with local host, will have to change if i test with vm. This field must be known out of code
	private int port;
	
	public Client() {
		this.port = 31415;
	}
	
	public Client(int p) {
		this.port = p;
	}
	
	//NOTE: same with server, this first thread can be made into the start method for client/server
	public void start() { //the reason why this look like this is because i made threads first then mod for client/server
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


			public Session(Socket s){
				this.soc = s;
				try {
					this.out = new ObjectOutputStream(soc.getOutputStream());
					this.in = new ObjectInputStream(soc.getInputStream());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				//innit session with a log in attempt, load it onto outbound queue to get ready to fire
				Message login = new Message(MessageType.VERIFICATION);
				synchronized (outbound) {
					addQueue(outbound, login);
				}
				//this is a client only feature
				
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
			
			//Start: listen for session
			private class ListenSession implements Runnable {
				@Override
				public void run() {
					try {
						System.out.println("start listen thread.");
						while(!soc.isClosed()) {
							Message bufferM = (Message) in.readObject();
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
				private boolean Logout = false;
				private Scanner scanner = new Scanner(System.in);
				private boolean Handshake = false;
				
				@Override
				public void run() {
					new Thread(new GUI()).start();
					System.out.println("start process thread.");
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
							new Thread(new Process(buffer)).start();
						}
					}	
				}
				
				private class GUI implements Runnable {
					@Override
					public void run() {
					    while (!Logout) {
					    	if (!Handshake) {
					            //log in msg success from server is the condition of handshake
								//until that is met this condition will keep this console ui blocked and unable to be shown to user.
								try {
								    Thread.sleep(100); // Avoid busy-waiting
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								continue;
							}
					    	
					    	// Take user input using GUI
					    	
//			                System.out.print("Enter text to send to the server: \n");
//			                String userInput = scanner.nextLine();
						
						    synchronized (outbound) {
						        addQueue(outbound, new Message("EY YO Server!!!!!!", MessageType.VERIFICATION));
						    }
				    	}
						scanner.close();
					} // end run()
				} // end GUI
				
				private class Process implements Runnable {
					private Message msg;
					
					public Process(Message m) {
						this.msg = m;
					}
					
					//for msg coming in that doesn't fit the designed case, it would simply be drop
					@Override
					public void run() {
						switch (msg.getType()) {
					    case VERIFICATION:
					        login();
					        break;
					    case SUCCESS:
					    	System.out.println("Message Sent By Server: ");
					    	String[] m = msg.getMessage();
					    	if (m != null && m.length > 0) {
					    	    for (String x : m) {
					    	        System.out.print(x);
					    	    }
					    	} else {
					    	    System.out.println("Message is empty or null.");
					    	}
					    default:
					    	break;
						}
					}
					
					//Check to establish basic handshake, once that is done we got a session and constantly send txt until logout bool is true
					public void login() {
						if (msg.getType().equals(MessageType.SUCCESS)) {
							System.out.println("Log in Success");
							Handshake = true;
						}
					}
//
//					
//					public void text() {
//						if (msg.getMessage().equals("SUCCESS")) {
//							System.out.println("Server response: " + msg.getMessage());
//						}
//					}
//					
//					public void logout() {
//						if (msg.getType().equals(MessageType.LOGOUT)) {
//							Logout = true;
//							try {
//								if (in != null)
//									in.close(); 
//								
//								if (out != null) 
//									out.close();
//								
//								if (soc != null && !soc.isClosed())
//									soc.close(); 
//							}
//							catch (IOException e) { 
//								e.printStackTrace(); // Handle exceptions properly } }
//							}
//						}
//					}
				} //end scope process 
			} //end scope processSession
			
			//Start: session sender
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

			
		}//end scope of session thread
		
    } //end scope of innitport thread
}

