package com.meritamerica.assignment4;

import java.util.Date;

public class TransferTransaction extends Transaction {
	public TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
		this.setSourceAccount(sourceAccount);
		this.setTargetAccount(targetAccount);
		this.setAmount(amount);
		this.setTransactionDate(new Date());
	}

	@Override
	public void setSourceAccount(BankAccount source) {		
	}

	@Override
	public void setTargetAccount(BankAccount target) {		
	}

}
