package client;

import resources.*;
import java.io.*;
import java.net.*;

import java.util.ArrayDeque;
import java.util.Queue;

import client.Client.ListenThread;
import client.Client.SendThread;

public class Client {
    private Message_Facade handler;
    private String IP;
    private int port = 31415;
	private Queue<Message> outbound = new ArrayDeque<>();
	private Queue<Message> inbound = new ArrayDeque<>();

    public Client() {
		this.IP = InetAddress.getInetAddress().getHostAddress();
    }
	public Client(int portNum){
		this.IP = InetAddress.getInetAddress().getHostAddress();
		this.port = portNum;
	}

    public void start() {
			try (Socket socket = new Socket("clientHost", this.port)) {
				
				new Thread(new SendThread(socket)).start();
				new Thread(new ProcessThread(socket)).start();
				new Thread(new ListenThread(socket)).start();
				

			}
			catch (IOException e) {
			}

    }

	//sync keyword to avoid add/pop at the same time to queue
	private synchronized void addQueue(Queue<Message> queue, Message message) {
        queue.add(message);
    }

	private synchronized Message popQueue(Queue<Message> queue) {
        return queue.poll();
    }

	//send monitor outbound queue and fork a thread to handle each item in queue until it is empty
    private class SendThread implements Runnable {
		@Override
        public void run() {
			while(true){ //TODO: update this with better condition instead of inti loop + close stream when it is no longer infi loop
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); //object out sender
				while(!outbound.isEmpty()){
					new Thread(new Sender(Message)).start();
				}
           	} 
		}
    }

	private class Sender implements Runnable {
		@Override
        public void run() {
			objectOutputStream.writeObject(popQueue());
		}
    }
    
	//process monitor inbound queue and fork a thread to handle each item in the queue until it is empty
	private class ProcessThread implements Runnable {
		@Override
        public void run() {
			//NOTE: infi loop to listen for object coming in (message), mod later id needed
            while(true){ //TODO: update this with better condition instead of inti loop + close stream when it is no longer infi loop
				while(!inbound.isEmpty()){
					new Thread(new Process()).start();
				}
			}
		}
    }

	private class Process implements Runnable {
		@Override
        public void run() {
			ClientFacade processer = new ClientFacade(popQueue(outbound)); //NOTE: placeholder, ideally we create a facade here in this thread with the Message passed in as args
			Message result = processer.process(); //placeholder 
			addQueue(outbound, result);
		}
    }

	//listen just listen and add to inbound queue
    private class ListenThread implements Runnable {
		@Override
        public void run() {
			//NOTE: infi loop to listen for object coming in (message), mod later id needed
            while(true){
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) objectInputStream.readObject();
				addQueue(inbound, msg);
			}
		}
    }
}

