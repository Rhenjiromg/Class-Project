package client;

import java.util.ArrayDeque;
import java.util.Queue;
import shared.Message;
import shared.Message;

public class GUIDriver {

	public static void main(String[] args) {

		Queue<Message> guiOutput = new ArrayDeque<>();

		GUI g = new GUI();
		String[] info = g.login();
		//
		// // print: name, password
		System.out.println(info[0] + ", " + info[1]);

		String[] s = new String[100];
		for (int x = 0; x < 100; ++x) {
			s[x] = "info";
		}
		// g.setUserPanel(s);
		//
		String[] s2 = new String[100];
		for (int x = 0; x < 100; ++x) {
			s2[x] = "TEMP DATA";
		}
		String[] l = new String[0];
		//
		// g.setDisplayPanel(s2);

		if (info[0].equals("") && info[1].equals("")) {
			// g.userDisplay(guiOutput);
			g.superUserDisplay(guiOutput);

			// update TEST
			try {
				Thread.sleep(1000); // add stuff after 1 second
				g.setDisplayPanelInfo(s2);
				g.setUserPanelInfo(s);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// separate thread for processing messages
			Thread queueProcessor = new Thread(() -> {
				while (true) {
					if (!guiOutput.isEmpty()) {
						Message m = guiOutput.poll();
						System.out.println("Message in Queue");
						for (String x : m.getMessage()) {
							System.out.println(x);
						}
						System.out.println(m.getType());
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			});

			queueProcessor.start();

			try {
				queueProcessor.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Main thread has finished.");

		} else {
			System.out.println("\nInvalid credentials, not cool!");
		}
	}
}
