package com.meritamerica.assignment4;

public class CDOffering {	
	private int term;
	private double interestRate;
	
	CDOffering(){
	}
	
	public CDOffering(int term, double interestRate) {
		this.term = term;
		this.interestRate = interestRate;
	}
	
	int getTerm() {
		return this.term;
	}
	
	double getInterestRate() {
		return this.interestRate;
	}
	
	public static CDOffering readFromString(String cdOfferingDataString) {
    		String [] temp = cdOfferingDataString.split(",");
    		CDOffering parsedOffer = new CDOffering(Integer.parseInt(temp[0]),Double.parseDouble(temp[1]));
    		return parsedOffer;
	}
	
	public String writeToString() {
		StringBuilder writeSB = new StringBuilder();
		return writeSB.append(term).append(",").append(interestRate).toString();
		}
}