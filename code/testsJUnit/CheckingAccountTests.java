package testsJUnit;

import static org.junit.jupiter.api.Assertions.*;
import account.CheckingAccount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


class CheckingAccountTests {

	@Test
	@DisplayName("Getters Setters / Constructor tests")
	void getterSetterTests() {
		CheckingAccount c = new CheckingAccount();
		
		System.out.println(c.getBalance());
		assertTrue(c.getBalance() == 0.0);
		
		CheckingAccount c2 = new CheckingAccount(5.0);
		
		System.out.println(c2.getBalance());
		assertTrue(c2.getBalance() == 5.0);
	}

}
