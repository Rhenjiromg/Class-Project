package testsJUnit;

<<<<<<< HEAD
import shared.Time;
import static org.junit.jupiter.api.Assertions.*;
=======
<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertEquals;

=======
import shared.Time;
import static org.junit.jupiter.api.Assertions.*;
>>>>>>> 2e524382b4d4bc83681bc408d6ad9ee940df57f4
>>>>>>> a9b181cafc03fa0189c27d6fdd36a38ce4333388
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import resources.Time;

class TimeTests {

	@Test
	@DisplayName("Testing Time class's current time method.")
	void test() {
		Time time = new Time();

		String timeCreated = time.toString().split(" ")[3];
		String seconds = timeCreated.split(":")[2];
		int secondsCreated = Integer.valueOf(seconds) % 60;

		try {
			Thread.sleep(5000); // sleep 5 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// since the current time has a 5 seconds different
		// we can assume the current date also works.
		// using the same technique
		String currentTime = time.getCurrentTime();
		String currSeconds = currentTime.split(":")[2];
		int currentSeconds = Integer.valueOf(currSeconds);

		// subtract 5 to match seconds created
		currentSeconds -= 5;
		currentSeconds %= 60;

		// if -5, -4, -3, -2, -1
		if (currentSeconds < 0) {
			currentSeconds += 60;
		}

		if (secondsCreated == currentSeconds) {
			System.out.println(String.valueOf(secondsCreated) + " equals " + String.valueOf(currentSeconds));
			System.out.println("Time test Passed.");
		} else {
			System.out.println(String.valueOf(secondsCreated) + " does not equal " + String.valueOf(currentSeconds));
			System.out.println("Time test Failed.");
		}

		assertEquals(secondsCreated, currentSeconds);
	}
}
