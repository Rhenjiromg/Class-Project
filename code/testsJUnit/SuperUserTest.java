package testsJUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.SuperUser;

class SuperUserTest {
	private SuperUser su;
	
	@Test
	void getterSetterTests() {
		su = new SuperUser("MRHacker", "SU001", "12345", "OPEN");
		assertEquals("MRHacker", su.getName());
		assertEquals("SU001", su.getID());
		assertEquals("12345", su.getPassword());
		assertEquals("OPEN", su.getState());
	}
}