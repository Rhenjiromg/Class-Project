import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuperUserTest {
	private SuperUser su;
	
	@BeforeEach
	void setUP() {
		su = new SuperUser("SU001", "12345");
	}
	
	@Test
	void testConstructor() {
	assertEquals("SU001", su.getName());
	assertEquals("12345", su.getPassword());

	}
	
	@Test
	void testsetName() {
		su.setName("SUPER");
		assertEquals("SUPER", su.getName());
	}
	
	@Test
	void testsetPassword() {
		su.setPassword("1234");
		assertEquals("1234", su.getPassword());
	}
	
	@Test
	void testsetID() {
		su.setID("001");
		assertEquals("001", su.getID());
	}
	
	@Test
	void testDeleteAccount() {
		su.deleteAccount();
	}
	
	@Test
	void testaddPerson() {
		su.addPerson();
	}
	
	@Test
	void testopenAccount() {
		su.openAccount();
	}
}