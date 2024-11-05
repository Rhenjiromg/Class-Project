package client;

import resources.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private Message msg;
    private Message_Facade handler;
    private String IP;
    private int port = 31415;

    public Client() {

    }
	public Client(int portNum){
		this.port = portNum;
	}

    public void start() {
    	this.IP = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket("clientHost", port);
            System.out.println("Connected to the server.");

		//1 thread in one thread out, each thread can handle more threads, 1 infi loop each
			new Thread(new SendThread(socket)).start();
            new Thread(new ListenThread(socket)).start();
    }

    static class SendThread implements Runnable {
        private Socket socket;

        public SendThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
			//currently check for console input for debugging frame, will change to check for gui action!
			while(true){
				//infi loop to keep checking for inputs (mod down here to change console -> gui)
				try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                	PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
               		String userInput;
               		while ((userInput = consoleReader.readLine()) != null) {
                    //call facade to make thread here; make mgs here
					Message msg = new Message(userInput, MessageType.VERIFICATION);
					new Thread(new resources.Facade_Frame(msg)).start();
                	}
           		} 
				catch (IOException ex) {
            	}
			}
        }
    }

    static class ListenThread implements Runnable {
        private Socket socket;

        public ListenThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
			//infi loop to listen for object coming in (message)
            while(true){
				try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
                	//call facade to make thread here
					Message msg = (Message) objectInputStream.readObject();
					new Thread(new resources.Facade_Frame(msg)).start();
				}
				catch (IOException ex) {
				}
			}
		}
    }
}
