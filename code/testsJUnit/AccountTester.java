package testsJUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import shared.Account;

class AccountTester {
	private Account acc1;
	private Account acc2;
	
	@BeforeEach
	void setUp() {
		//Set up two accounts for testing
		acc1 = new Account();
		acc2 = new Account();
	}
	
	@Test
	void testDefaultConstructor() {
		/* Testing the default account constructor */
		assertNotNull(acc1.getAccountID());
		assertEquals(0.0, acc1.getBalance());
		// assertEquals(LocalDate.now(), acc1.creationDate);
		// assertEquals(acc1.creationDate, acc1.lastCheck);
		
	}

	@Test
	void testParamsConstructor() {
		/* Testing parameters for the constructor */
		String accID = "1001";
		String balance = "200.0";
		String creationDate = "2024-12-01";
		String lastCheckDate = "2024-11-30";
		
		Account acc = new Account(accID, balance, creationDate, lastCheckDate);
		
		assertEquals(accID, acc.getAccountID());
		assertEquals(Double.parseDouble(balance), acc.getBalance());
		// assertEquals(LocalDate.parse(creationDate), acc.creationDate);
		// assertEquals(LocalDate.parse(lastCheckDate), acc.lastCheck);
		
	}
	
	@Test
	void testDeposit() {
		/* Testing the Deposit method */
		acc1.deposit(100.0);
		assertEquals(100.0, acc1.getBalance());
	}
	
	@Test
	void testWorkingWithdraw() {
		/* Testing Withdraw method */
		acc1.deposit(450.0);
		boolean working = acc1.withdraw(250.0);
		
		assertTrue(working);
		assertEquals(200.0, acc1.getBalance());
		
	}
	
	@Test
	void testOverdraftWithdraw() {
		/* Testing for Overdrafts when withdrawing */
		acc1.deposit(200.0);
		boolean working = acc1.withdraw(250.0);
		assertFalse(working);
		assertEquals(200.0, acc1.getBalance());
	}
	
	@Test
	void testTransferFunds() {
		/* Testing TransferFunds method */
		acc1.deposit(250.0);
		boolean working = acc1.transferFunds(acc2, 150.0);
		assertTrue(working);
		assertEquals(100.0, acc1.getBalance());
		assertEquals(150.0, acc2.getBalance());
		
	}
	
	@Test
	void testTransferFundsOverdraft() {
		/* Testing for OverDrafts when transferring account funds */
		acc1.deposit(200.0);
		boolean working = acc1.transferFunds(acc2, 400.0);
		assertFalse(working);
		assertEquals(200.0, acc1.getBalance());
		assertEquals(0.0, acc2.getBalance());
		
	}
	
	@Test
	void testFilePrep() {
		/* Testing the filePrep method */
		acc1.deposit(400.0);
		ArrayList<String> file = acc1.filePrep();
		
		assertEquals(4, file.size());
		assertEquals(acc1.getAccountID(), file.get(0));
		assertEquals(String.valueOf(acc1.getBalance()), file.get(1));
		// assertEquals(acc1.creationDate.toString(), file.get(2));
		// assertEquals(acc1.lastCheck.toString(), file.get(3));
		
	}
	
}
