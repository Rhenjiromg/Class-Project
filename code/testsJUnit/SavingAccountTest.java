package testsJUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import shared.SavingAccount;
import shared.Account;

import java.time.LocalDate;
import java.util.ArrayList;

class SavingsAccountTester {

	private SavingAccount savings;
	private Account acc;
	
	@BeforeEach
	void setUp() {
		savings = new SavingAccount("1001",  "1000.0", "2024-01-12", "2024-01-12", "0");
		acc = new Account("1001", "400.0", "2023-01-12", "2024-01-12");
	}
	
	@Test
	@DisplayName(value = "Withdraw Limits")
	void testWithdrawLimit() {
		/* Tester for Withdrawing within limits of savings */
		boolean working = savings.withdraw(200.0);
		assertTrue(working);
		assertEquals(800.0, savings.getBalance());
		
		//Check for the amount of withdraws (Should be 1)
		assertEquals(1, savings.filePrep().get(5));
	}
	
	@Test
	@DisplayName(value = "Withdraw Exceeds Limits")
	void testWithdrawExceedsLimits() {
		/* Tester for withdrawing when exceeding the limits */
		
		//For loop to create withdraws that exceed limits
		for(int i = 0; i < 6; i++) {
			savings.withdraw(100.0);
		}
		//The penalty should be triggered with this withdraw
		savings.withdraw(100.0);
		
		assertEquals(245.0, savings.getBalance()); // 6 withdrawals adding the penalty
		assertEquals(0, Integer.parseInt(savings.filePrep().get(5))); //Reset numberOfWithdrawals
	}
	
	@Test
	@DisplayName(value = "Withdraw With a Penalty")
	void testWithdrawWithPenaltyExceedsBalance() {
		/* Tester for withdrawing with a penalty that 
		 * exceeds savings account balance */
		
		//Create a balance that is lower than the penalty
		savings.setBalance(2.0);
		
		//Withdraw more than what is in account
		for(int i = 0; i < 7; i++) {
			savings.withdraw(1.0);
		}
		//There should be no balance after the penalty
		assertEquals(0.0,savings.getBalance());
	}
	
	@Test
	@DisplayName(value = "Trandfer Funds Limits")
	void testTransferFundsLimit() {
		/* Tester for transferring funds that are within 
		 * limits of a savings account */
		boolean working = savings.transferFunds(acc, 500.0);
		assertTrue(working);
		assertEquals(500.0, savings.getBalance());
		assertEquals(1, savings.filePrep().get(5)); //numberOfWithdrawals incremented
	}
	
	@Test
	@DisplayName(value = "Apply Interest")
	void testApplyInterest() {
		/* Tester for applying the interest rate to a savings account */
		savings.setBalance(2000.0);
		
		// savings.lastCheck = savings.lastCheck.minusYears(2);
		
		savings.applyInrest();
		
		double expectedBalance = 2000.0 * Math.pow(1.1, 2); //10% interest after 2 years
		assertEquals(expectedBalance, savings.getBalance());
		// assertEquals(LocalDate.now(), savings.lastCheck);
	}
	
	@Test
	@DisplayName(value = "File Prep")
	void testFilePrep() {
		/* Tester for FilePrep */
		ArrayList<String> file = savings.filePrep();
		
		assertEquals(6, file.size());
		assertEquals("1001", file.get(0)); //ID
		assertEquals("2000.0", file.get(1)); // Balance
		assertEquals("2024-01-12", file.get(2)); // creationDate
		assertEquals("2024-01-12", file.get(3)); //lastCheck
		assertEquals("5.0", file.get(4)); // Penalty
		assertEquals("0", file.get(5)); //numberOfWithdrawals
		
	}
	
	@Test
	@DisplayName(value = "Setting Interest Rate")
	void testSetInterestRate() {
		/* Tester for setting interest rate */
		savings.setInterestRate(0.4);
		assertEquals(0.4, savings.getInterestRate());
	}

}