package com.meritamerica.assignment4;

public class FraudQueue {
	private FraudNode head;
	private FraudNode tail;
	//We are just making the list itself, which honestly just involves making the start and end values
	//and associating them with a name when we use this constructor. We will add nodes to the list in the following methods.
	public FraudQueue() {
		this.head = null;
		this.tail = null;
	}
	
	//Now we add to the list. We first pass the data into a FraudNode,
	//then add that temp node to the list by putting it into the queue.
	public void addTransaction(Transaction data) {
		FraudNode temp = new FraudNode(data);
		//Doing a check to make sure this list isn't empty. If it is, the
		//head and tail values both need to point to this one node. After all,
		//a list with one item makes that item the first and last all at the same time.
		if (this.tail == null) {
			this.head = this.tail = temp;
		}
		//if it's not empty, we add it in a much more normal fashion. We first make the
		//last node's next point to this temp node:
		this.tail.next = temp;
		//then, because it's one longer, we move the tail indicator to this new, most recent node
		this.tail = temp;
	}

	public Transaction getTransaction() {
		//Gotta check for empty lists
		if (this.head == null) {
			return null;
		}
		else {
			//We store the info of the first node (first person in the queue)
			//in a temp spot, then move the head indicator back one.
			FraudNode temp = this.head;
			this.head = head.next;
			//Then, we return the old head's data, which is a transaction.
			return temp.data;
		}
	}
	
	public int getSize() {
		FraudNode temp = head;
		int count = 0;
		while (temp.next != null) {
			count++;
		}
		return count;
	}
	
	public FraudNode getHead() {
		return this.head;
	}
}
