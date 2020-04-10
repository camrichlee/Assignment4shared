package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CDAccount extends BankAccount {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date startDate;
	private int term;
	
	//CDAccount Constructor passing up to Bank Account Constructor 1
	CDAccount(CDOffering offering, double openingBalance) {
		super(openingBalance, offering.getInterestRate());
		this.term = offering.getTerm();
	}
	
	//CDAccount Constructor passing up to Bank Account Constructor 2
	CDAccount(CDOffering offering, double openingBalance, java.util.Date accountOpenedOn) {
		super(openingBalance, offering.getInterestRate(), accountOpenedOn);
		this.term = offering.getTerm();
	}
	
	//CDAccount Constructor passing up to Bank Account Constructor 3
	CDAccount(long accountNumber, CDOffering offering, double openingBalance, java.util.Date accountOpenedOn) {
		super(accountNumber, openingBalance, offering.getInterestRate(), accountOpenedOn);
		this.term = offering.getTerm();
	}
	
	//Alternate CDACcount Constructor passing up to Band Account Constructor 3
	CDAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn, int term){
		super(accountNumber, balance, interestRate, accountOpenedOn);
		this.term = term;
	}
	
	public CDAccount (BankAccount bankAccount, int term) {
		super(bankAccount.getAccountNumber(), bankAccount.getBalance(), bankAccount.getInterestRate(), bankAccount.getOpenedOn());
		this.term = term;
	}
	
	public double getBalance() {
		return super.getBalance();
	}
	
	public double getInterestRate() {
		return super.getInterestRate();
	}
	
	int getTerm() {
		return this.term;
	}
	
	Date getStartDate() {
		return this.startDate;
	}
	
	public boolean withdraw(double amount) {
		return false;
	}
	
	public boolean deposit(double amount) {
		return false;
	}
	
	public long getAccountNumber() {
		return super.getAccountNumber();
	}
	
	double futureValue() {
		double futureReturn = super.futureValue(this.term);
		return futureReturn;
	}
	
	public static CDAccount readFromString(String accountData) {
		//Ask Sadiq about out of order parsing
		SimpleDateFormat cdDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
		String [] temp = accountData.split(",");
		long cdNum = Long.parseLong(temp[0]);
		double cdBal = Double.parseDouble(temp[1]);
		double cdInter = Double.parseDouble(temp[2]);
		Date parsedDate = cdDate.parse(temp[3]);
		int cdTerm = Integer.parseInt(temp[4]);
		CDAccount newAcct = new CDAccount(cdNum, cdBal, cdInter, parsedDate, cdTerm);
		return newAcct;
		}
		catch (ParseException e) {
			return null;
		}
	}
	
	public String writeToString() {
		String temp = super.writeToString();
		StringBuilder writeSB = new StringBuilder();
		return writeSB.append(temp).append(",").append(term).toString();
		}
}