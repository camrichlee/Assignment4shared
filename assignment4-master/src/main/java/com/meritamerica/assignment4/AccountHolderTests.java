package com.meritamerica.assignment4;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AccountHolderTests {
	@Test
	public void invalidSSNTest() {
		AccountHolder testHolder = new AccountHolder();
		assertFalse(testHolder.setSSN("123"));
	}
	
	@Test
	public void validSSNTest() {
		AccountHolder testHolder = new AccountHolder();
		assertTrue(testHolder.setSSN("123456789"));
		assertEquals("123456789", testHolder.getSSN());
	}
	
	@Test
	public void balanceTooLargeChecking() {
		AccountHolder testHolder = new AccountHolder();
		CheckingAccount testChecking = new CheckingAccount(999.99);
		ExceedsCombinedBalanceLimitException testThrow = 
				assertThrows(ExceedsCombinedBalanceLimitException.class, 
						() -> testHolder.addCheckingAccount(testChecking), "");
	}
	
}
