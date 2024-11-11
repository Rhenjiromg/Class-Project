package client;

import resources.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import java.util.ArrayDeque;
import java.util.Queue;

import client.Client.ListenThread;
import client.Client.SendThread;

public class Client {
	private MessageFacade handler;
	private String IP;
	private int port = 31415;
	private Queue<Message> outbound = new ArrayDeque<>();
	private Queue<Message> inbound = new ArrayDeque<>();
	private HashMap<Message, Socket> receipt = new HashMap<>();

	public Client() {
		try {
			InetAddress IA = InetAddress.getLocalHost();
			this.IP = IA.getHostAddress();
		} catch (Exception e) {

		}
	}

	public Client(int portNum) {
		try {
			InetAddress IA = InetAddress.getLocalHost();
			this.IP = IA.getHostAddress();
			this.port = portNum;
		} catch (Exception e) {

		}
	}

	// for some reason, this is the constructor called, in facade frame.
	public Client(String string, MessageFacade msg) {
		// TODO Auto-generated constructor stub
	}

	public void start() {
		try (Socket socket = new Socket("clientHost", this.port)) {

			// commented out for errors
			// new Thread(new SendThread(socket)).start();
			// new Thread(new ProcessThread(socket)).start();
			// new Thread(new ListenThread(socket)).start();

		} catch (IOException e) {
		}

	}

	// sync keyword to avoid add/pop at the same time to queue
	private synchronized void addQueue(Queue<Message> queue, Message message) {
		queue.add(message);
	}

	private synchronized Message popQueue(Queue<Message> queue) {
		return queue.poll();
	}

	private synchronized void addPair(Message message, Socket socket) {
		// commented out for error
		// messageSocketMap.put(message, socket);
	}

	// this is essentially a pop: get socket from key message then remove it from
	// map
	private synchronized Socket removePair(Message message) {
		// commented out for error
		// return messageSocketMap.remove(message);
		// also a placeholder return
		return new Socket();
	}

	// send monitor outbound queue and fork a thread to handle each item in queue
	// until it is empty
	public class SendThread implements Runnable {
		@Override
		public void run() {
			while (true) { // TODO: update this with better condition instead of inti loop + close stream
							// when it is no longer infi loop
				while (!outbound.isEmpty()) {
					new Thread(new Sender(popQueue(outbound))).start();
				}
			}
		}
	}

	// sub thread sen
	private class Sender implements Runnable {
		private Message m;
		private Socket s;

		public Sender(Message msg) {
			this.m = msg;
			this.s = removePair(msg);
		}

		@Override
		public void run() {
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.s.getOutputStream()); // object out
																											// sender
				objectOutputStream.writeObject(this.m);
				objectOutputStream.flush();
				objectOutputStream.close();
				this.s.close();
			} catch (Exception e) {
			}
		}
	}

	// process monitor inbound queue and fork a thread to handle each item in the
	// queue until it is empty
	private class ProcessThread implements Runnable {
		@Override
		public void run() {
			// NOTE: infi loop to listen for object coming in (message), mod later id needed
			while (true) { // TODO: update this with better condition instead of inti loop + close stream
							// when it is no longer infi loop
				while (!inbound.isEmpty()) {
					new Thread(new Process()).start();
				}
			}
		}
	}

	// sub thread process

	/**
	 * Commented out by Rhenji, 11/10 for error
	 * 
	 */
	private class Process implements Runnable {
		@Override
		public void run() {
			/*
			 * ClientFacade processer = new ClientFacade(popQueue(inbound)); // NOTE:
			 * // placeholder, ideally we create a
			 * // facade here in this thread with the
			 * // Message passed in as args
			 * Message result = processer.process(); // NOTE: placeholder
			 * addQueue(outbound, result);
			 */
		}
	}

	// listen just listen and add to inbound queue
	public class ListenThread implements Runnable {
		@Override
		public void run() {

			ServerSocket serverSocket = new ServerSocket(port);
			// NOTE: infi loop to listen for object coming in (message), mod later id needed
			while (true) {
				Socket client = serverSocket.accept();
				new Thread(new Listen(client)).start();
			}
			serverSocket.close(); // TODO: once loop condition is updated, this will clean up serverSocket
		}
	}

	// sub thread process
	private class Listen implements Runnable {
		private Socket s;

		public void Sender(Socket soc) {
			this.s = soc;
		}

		@Override
		public void run() {
			ObjectInputStream objectInputStream = new ObjectInputStream(this.s.getInputStream());
			Message msg = (Message) objectInputStream.readObject();
			addQueue(inbound, msg);
			addPair(msg, s);
			objectInputStream.close();
		}
	}

	/**
	 * TODO: add these functions. Currently called in facade frame java.
	 */
	public void on() {
	}

	public void off() {
	}
}
