package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {
	//TODO figure out if there are missing variables
	private double amount;
	private BankAccount sourceAccount;
	private BankAccount targetAccount;
	private java.util.Date transactionDate;
	
	public BankAccount getSourceAccount() {
		return this.sourceAccount;
	}
	public abstract void setSourceAccount(BankAccount source);
	
	public BankAccount getTargetAccount() {
		return this.targetAccount;
	}
	public abstract void setTargetAccount(BankAccount target);
	
	public double getAmount() {
		return this.amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public java.util.Date getTransactionDate() {
		return this.transactionDate;
	}
	public void setTransactionDate(java.util.Date date) {
		this.transactionDate = date;
	}
	
	public String writeToString() {	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder toString = new StringBuilder();
		if(sourceAccount == null) {
			toString.append(-1);
		}
		else {
			toString.append(sourceAccount.getAccountNumber());
		}
		toString.append(",");
		toString.append(targetAccount.getAccountNumber());
		toString.append(",");
		toString.append(amount);
		toString.append(",");
		toString.append(dateFormat.format(transactionDate));
		return toString.toString();
	}
	
	public static Transaction readFromString(String transactionData) throws ParseException {
		String[] temp = transactionData.split(",");
		BankAccount source;
		if(temp[0].equals("-1")) {
			source = null;
		}
		else {
			source = MeritBank.getBankAccount(Long.valueOf(temp[0]));
		}
		BankAccount target = MeritBank.getBankAccount(Long.valueOf(temp[1]));
		double amount = Double.valueOf(temp[2]);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = df.parse(temp[3]);
		if(Integer.valueOf(temp[0]) == -1) {
			if(Double.valueOf(temp[2]) < 0) {
				WithdrawTransaction transaction = new WithdrawTransaction(target, amount);
				transaction.setTransactionDate(date);
				System.out.println(transaction.writeToString());
				return transaction;
			}
			else {
				DepositTransaction transaction = new DepositTransaction(target, amount);
				transaction.setTransactionDate(date);
				System.out.println(transaction.writeToString());
				return transaction;
			}
		}
		else {
			TransferTransaction transaction = new TransferTransaction(source, target, amount);
			System.out.println(transaction.writeToString());
			return transaction;
		}
	}
	
	
}
