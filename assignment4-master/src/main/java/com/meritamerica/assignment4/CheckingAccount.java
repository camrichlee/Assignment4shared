package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Sub-class of Bank Account Superclass
public class CheckingAccount extends BankAccount {
    DecimalFormat df2 = new DecimalFormat("#.00");
    
	//Checking Constructor that passes up to Bank Account Constructor 1
	CheckingAccount(double openingBalance) {
		super(openingBalance, 0.0001);
	}
	
	//Checking Constructor that passes up to Bank Account Constructor 2
	CheckingAccount(double openingBalance, java.util.Date accountOpenedOn) {
		super(openingBalance, 0.0001, accountOpenedOn);
	}
	
	//Checking Constructor that passes up to Bank Account Constructor 3
	CheckingAccount(long accountNumber, double openingBalance, java.util.Date accountOpenedOn) {
		super(accountNumber, openingBalance, 0.0001, accountOpenedOn);
	}
	
	CheckingAccount(long accountNumber, double openingBalance, double interestRate, java.util.Date accountOpenedOn) {
		super(accountNumber, openingBalance, interestRate, accountOpenedOn);
	}
	
	//Savings Constructor for the ReadFromString Method
	public CheckingAccount (BankAccount bankAccount) {
		super(bankAccount.getAccountNumber(), bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getOpenedOn());
	}
	
	public static CheckingAccount readFromString(String accountData) {
		SimpleDateFormat cdDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
    		String [] temp = accountData.split(",");
    		long bankNum = Long.parseLong(temp[0]);
    		double bankBal = Double.parseDouble(temp[1]);
    		double bankInter = Double.parseDouble(temp[2]);
    		Date date = cdDate.parse(temp[3]);
    		CheckingAccount newAcct = new CheckingAccount(bankNum, bankBal, bankInter, date);
    		return newAcct;
    	}
    	catch(ParseException  e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public String toString() {
		String returnPlaceholder = "Checking Account Balance: $" + df2.format(getBalance()) +
						"\nChecking Account Interest Rate: " + getInterestRate() +
						"\nChecking Account Balance in 3 years: $" + df2.format(futureValue(3));
		return returnPlaceholder;
	}
}