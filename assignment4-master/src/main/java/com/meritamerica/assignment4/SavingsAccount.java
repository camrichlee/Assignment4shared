package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.ParseException;
//import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends BankAccount {
    DecimalFormat df2 = new DecimalFormat("#.00");
    
	//Savings Constructor that passes up to Bank Account Constructor 1
	SavingsAccount(double openingBalance) {
		super(openingBalance, 0.01);
	}
	
	//Savings Constructor that passes up to Bank Account Constructor 2
	SavingsAccount(double openingBalance, java.util.Date accountOpenedOn) {
		super(openingBalance, 0.01, accountOpenedOn);
	}
	
	//Savings Constructor that passes up to Bank Account Constructor 3
	SavingsAccount(long accountNumber, double openingBalance, java.util.Date accountOpenedOn) {
		super(accountNumber, openingBalance, 0.01, accountOpenedOn);
	}
	//Savings Constructor that passes up to Bank Account Constructor 3
	SavingsAccount(long accountNumber, double openingBalance, double interestRate, java.util.Date accountOpenedOn) {
		super(accountNumber, openingBalance, interestRate, accountOpenedOn);
	}
	
	//Savings Constructor for the ReadFromString Method
	public SavingsAccount (BankAccount bankAccount) {
		super(bankAccount.getAccountNumber(),bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getOpenedOn());
	}
	
	public static SavingsAccount readFromString(String accountData) {
		SimpleDateFormat cdDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
    		String [] temp = accountData.split(",");
    		long bankNum = Long.parseLong(temp[0]);
    		double bankBal = Double.parseDouble(temp[1]);
    		double bankInter = Double.parseDouble(temp[2]);
    		Date date = cdDate.parse(temp[3]);
    		SavingsAccount newAcct = new SavingsAccount(bankNum, bankBal, bankInter, date);
    		return newAcct;
    	}
    	catch(ParseException  e) {
    		e.printStackTrace();
    		return null;
    	}
	}

}