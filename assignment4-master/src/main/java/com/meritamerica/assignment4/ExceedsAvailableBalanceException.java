package com.meritamerica.assignment4;

public class ExceedsAvailableBalanceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceedsAvailableBalanceException(String errorMessage) {
		super (errorMessage);
	}
}
