package com.meritamerica.assignment4;

//Since our fraud queue is a linked list, we set up how each node is constructed first.
public class FraudNode {
	Transaction data;
	FraudNode next;

	public FraudNode(Transaction data) {
		this.data = data;
		this.next = null;
	}
}
