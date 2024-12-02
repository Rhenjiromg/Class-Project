package testsJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import shared.Account;
import shared.CheckingAccount;

class CheckingAccountTests {
	
	private CheckingAccount checking;
	private Account acc;
	
	
	@BeforeEach
	@DisplayName(value = "Set-Up")
	void setUp() {
		/* Create checking account and regular account for 
		 * testing */
		checking = new CheckingAccount("1001", "600.0", "2024-01-12", "2024-01-12");
		acc = new Account("1002", "200.0", "2024-01-12", "2024-01-12");
	}
	
	@Test
	@DisplayName(value = "Deposit Above Fee Limit")
	void testDepositAboveFee() {
		checking.deposit(200.0);
		assertEquals(800.0, checking.getBalance());
	}
	
	@Test
	@DisplayName(value = "Withdraw Above Fee Limit")
	void testWithdrawAboveFee() {
		boolean working = checking.withdraw(200.0);
		assertTrue(working);
		assertEquals(400.0, checking.getBalance());
	}
	
	@Test
	@DisplayName(value = "Withdraw within Fee Limit")
	void testWithdrawBelowFee() {
		checking.setBalance(80.0);
		boolean working = checking.withdraw(30.0);
		assertTrue(working);
		assertEquals(45.0, checking.getBalance());
	}
	
	@Test
	@DisplayName(value = "Withdaw with Insufficient Funds ")
	void testWithdrawInsufficientFunds() {
		
		checking.setBalance(4.0); //Set balance below fee
		boolean working = checking.withdraw(3.0); //Withdraw will cancel due to insufficient funds
		assertTrue(working);
		assertEquals(1.0, checking.getBalance()); //balance should be the same
	}
	
	@Test
	@DisplayName(value = "Tranfer Funds Above Fee Limit")
	void testTransferFundsWithFee() {
		boolean working = checking.transferFunds(acc, 300.0);
		assertTrue(working);
		assertEquals(300.0, checking.getBalance());
		assertEquals(500, acc.getBalance());
	}
	
	@Test
	@DisplayName(value = "Transfer Funds Below Fee Limit")
	void testTransferFundsBelowFee() {
		checking.setBalance(50.0);
		boolean working = checking.transferFunds(acc, 30.0);
		assertTrue(working);
		assertEquals(15.0, checking.getBalance());
		assertEquals(230.0, acc.getBalance());
	}
	
	@Test
	@DisplayName(value = "Fee Not Applied on Low Balance")
	void testFeePlusLowBalance() {
		checking.setBalance(4.0); //Balance set below fee
		checking.deposit(10.0); //Deposit shouldn't fee since a new balance is less than 100
		
		assertEquals(9.0, checking.getBalance());
	}
	
	@Test
	@DisplayName(value = "File Prep Test")
	void testFilePrep() {
		var file = checking.filePrep();
		assertEquals(4, file.size());
		assertEquals("1001", file.get(0)); //ID
		assertEquals("600.0", file.get(1)); //Initial Balance
	}
	
}

