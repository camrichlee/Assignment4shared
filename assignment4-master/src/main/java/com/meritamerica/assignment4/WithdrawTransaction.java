package com.meritamerica.assignment4;

import java.util.Date;

public class WithdrawTransaction extends Transaction {
	public WithdrawTransaction(BankAccount targetAccount, double amount) {
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
