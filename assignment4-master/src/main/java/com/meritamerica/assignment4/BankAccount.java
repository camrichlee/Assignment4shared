package com.meritamerica.assignment4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;

//SuperClass
public class BankAccount {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat df2 = new DecimalFormat("#.00");
	private double balance;
	private double interestRate;
	private java.util.Date accountOpenedOn;
	private long accountNumber;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>(1);
	
	//SuperConstructor 1
	BankAccount(double balance, double interestRate){
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = new Date();
	}
	
	//SuperConstructor 2
	BankAccount(double balance, double interestRate, java.util.Date accountOpenedOn){
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
	}
	
	//SuperConstructor 3
	BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn){
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return this.balance;
	}

	public double getInterestRate() {
		return this.interestRate;
	}

	public java.util.Date getOpenedOn() {
		return this.accountOpenedOn;
	}

	public long getAccountNumber() {
		return this.accountNumber;
	}
	
	public void addTransaction(Transaction tran) {
		this.transactions.add(tran);
	}
	
	public List<Transaction> getTransactions() {
		return this.transactions;
	}
	
	public boolean withdraw(double amount) {
		if ((amount > 0) && (amount <= this.balance)) {
			this.balance -= amount;
			System.out.println("\nWithdrawl of $" + df2.format(amount) + " Successful\n");
			return true;
		}
		else {
			System.out.println("\nWithdrawl Error");
			return false;
		}
	}
	
	public boolean deposit(double amount) {
		if (amount > 0) {
			this.balance += amount;
			System.out.println("\nDeposit of $" + df2.format(amount) + " Successful\n");
			return true;
		}
		else {
			System.out.println("\nDeposit Error");
			return false;
		}
	}
	
	//
	double futureValue(int years) {
		return (this.balance * Math.pow((1 + interestRate), years));
	}
	
	//Taking in a string to create a new account
	public static BankAccount readFromString(String accountData) throws ParseException, NumberFormatException{
		SimpleDateFormat cdDate = new SimpleDateFormat("dd/MM/yyyy");
		try {
    		String [] temp = accountData.split(",");
    		long bankNum = Long.parseLong(temp[0]);
    		double bankBal = Double.parseDouble(temp[1]);
    		double bankInter = Double.parseDouble(temp[2]);
    		Date date = cdDate.parse(temp[3]);
    		BankAccount newAcct = new BankAccount(bankNum, bankBal,bankInter,date);
    		return newAcct;
    	}
    	catch(ParseException  e) {
    		e.printStackTrace();
    		return null;
    	}

    }
	
	public String writeToString() {
		StringBuilder writeSB = new StringBuilder();
		return writeSB.append(accountNumber).append(",").append(balance).append(",").append(interestRate).append(",").append(sdf.format(accountOpenedOn)).toString();
	}
	
}