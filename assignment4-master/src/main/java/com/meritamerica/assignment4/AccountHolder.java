package com.meritamerica.assignment4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//We are making this a public class, and extending the Comparable interface. 
//The reason for passing in this <AccountHolder> tag is because when using Comparable,
//we want to control the type of object allowed to be passed into it.

public class AccountHolder implements Comparable<AccountHolder> {
	//Here's where we need to declare all the variables we will use.
	//This is especially important for constructing Arrays of these objects,
	//Since the data we define inside of the class will help determine how large
	//each allocated array slot should be, memory-wise.
	private int initialArraySize;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private CheckingAccount[] checkingAccountArray = new CheckingAccount[initialArraySize];
	private SavingsAccount[] savingsAccountArray = new SavingsAccount[initialArraySize];
	private CDAccount[] cdAccountArray = new CDAccount[initialArraySize];
	private String firstName;
	private String lastName;
	private String middleName;
	private String ssn;
	
	//Default constructor
	AccountHolder(){
	}
	
	//Main Constructor
	AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		setFirstName (firstName);
		setMiddleName(middleName);
		setLastName  (lastName);
		setSSN       (ssn);
		this.initialArraySize = 0;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return this.middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getSSN() {
		return this.ssn;
	}
	
	public Boolean setSSN(String ssn) {
		if (ssn.length() == 9) {
				this.ssn = ssn;
				return true;
			}
		else {
			System.out.println("Please enter a valid 9-digit SSN");
			return false;
		}
	}
	
	/**
	 * Adds a checking account to the current Account Holder. This is the first step in a 2 method pair.
	 * @param openingBalance The balance the account will begin with.
	 * @return				The created checking account.
	 * @throws ExceedsCombinedBalanceLimitException
	 */
	CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		CheckingAccount placeholderCheckingAccount = new CheckingAccount(checkingAccountArray.length - 1, openingBalance, new Date());
		addCheckingAccount(placeholderCheckingAccount);
		return placeholderCheckingAccount;
	}
	
	/**
	 * This is the second part of creating a checking account. The first creates the actual object, this one associates it with
	 * the proper account holder. This not only allows us to create checking accounts that do not initially have account holders
	 * for testing purposes, but also to pass existing accounts to account holders without creation. And for doing both at once, 
	 * we have this lovely 2 part method.
	 * First, the holder's account array is increased in size by 1, and then the opening balance is checked.
	 * If the holder is carrying a balance over the given limit, we throw and exception and stop there. 
	 * @param checking	The checking account we created in the first part of the 2-method pair
	 * @return			The checking account added to the holder's array.
	 * @throws ExceedsCombinedBalanceLimitException
	 */
	CheckingAccount addCheckingAccount(CheckingAccount checking) throws ExceedsCombinedBalanceLimitException {
		//Array size increase
		CheckingAccount[] tempAccountArray = new CheckingAccount[checkingAccountArray.length + 1];
		for (int i = 0; i < checkingAccountArray.length; i++) {
			tempAccountArray[i] = checkingAccountArray[i];
		}
		checkingAccountArray = (CheckingAccount[])tempAccountArray.clone();
		//Balance checks
		double checkingBalance = getCheckingBalance();
		double savingsBalance = getSavingsBalance();
		if ((checkingBalance + savingsBalance + checking.getBalance()) > 250000.00) {
			throw new ExceedsCombinedBalanceLimitException("CombinedBalanceLimitException");	
		}
		else {
		checkingAccountArray[checkingAccountArray.length - 1] = checking;
		return checking;
		}
	}
	
	CheckingAccount[] getCheckingAccounts() {
		return this.checkingAccountArray;
	}
	
	int getNumberOfCheckingAccounts() {
		return checkingAccountArray.length;
	}
	
	public double getCheckingBalance() {
		double checkingBalance = 0;
		for (CheckingAccount i: checkingAccountArray) {
			checkingBalance += i.getBalance();
		}
		return checkingBalance;
	}
	
	//TODO throw ExceedsCoimbinedBalanceLimit Exception, add a deposit transaction with the opening balance.
	SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		SavingsAccount placeholderSavingsAccount = new SavingsAccount(savingsAccountArray.length - 1, openingBalance, new Date());
		addSavingsAccount(placeholderSavingsAccount);
		return placeholderSavingsAccount;
	}
	
	SavingsAccount addSavingsAccount(SavingsAccount savings) throws ExceedsCombinedBalanceLimitException {
		//Array size increase
		SavingsAccount[] tempAccountArray = new SavingsAccount[savingsAccountArray.length + 1];
		for (int i = 0; i < checkingAccountArray.length; i++) {
			tempAccountArray[i] = savingsAccountArray[i];
		}
		savingsAccountArray = (SavingsAccount[])tempAccountArray.clone();
		//Balance checks
		double checkingBalance = getCheckingBalance();
		double savingsBalance = getSavingsBalance();
		if ((checkingBalance + savingsBalance + savings.getBalance()) > 250000.00) {
			throw new ExceedsCombinedBalanceLimitException("CombinedBalanceLimitException");	
		}
		else {
		savingsAccountArray[savingsAccountArray.length - 1] = savings;
		return savings;
		}
	}
	
	SavingsAccount[] getSavingsAccounts() {
		return savingsAccountArray;
	}
	
	int getNumberOfSavingsAccounts() {
		return savingsAccountArray.length;
	}
	
	public double getSavingsBalance() {
		double savingsBalance = 0;
		for (SavingsAccount i: savingsAccountArray) {
			savingsBalance += i.getBalance();
		}
		return savingsBalance;
	}
	
	//TODO add deposit transaction with opening balance.
	CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsCombinedBalanceLimitException {
		if (offering == null) {
			return null;
		}
		CDAccount cdPlaceholder = new CDAccount(cdAccountArray.length - 1, offering, openingBalance, new Date());
		addCDAccount(cdPlaceholder);
		return cdPlaceholder;
	}
	
	CDAccount addCDAccount(CDAccount cdNew) throws ExceedsCombinedBalanceLimitException {
		//Array size increase
		CDAccount[] tempAccountArray = new CDAccount[cdAccountArray.length + 1];
		for (int i = 0; i < cdAccountArray.length; i++) {
			tempAccountArray[i] = cdAccountArray[i];
		}
		cdAccountArray = (CDAccount[])tempAccountArray.clone();
		//Balance checks
		double checkingBalance = getCheckingBalance();
		double savingsBalance = getSavingsBalance();
		if ((checkingBalance + savingsBalance + cdNew.getBalance()) > 250000.00) {
			throw new ExceedsCombinedBalanceLimitException("CombinedBalanceLimitException");	
		}
		else {
		cdAccountArray[cdAccountArray.length - 1] = cdNew;
		return cdNew;
		}
	}
	
	CDAccount[] getCDAccounts() {
		return this.cdAccountArray;
	}
	
	int getNumberOfCDAccounts() {
		return cdAccountArray.length;
	}

	public double getCDBalance() {
		double cdBalance = 0;
		for (CDAccount i: cdAccountArray) {
			cdBalance += i.getBalance();
		}
		return cdBalance;
	}
	
	public double getCombinedBalance() {
		double savings = this.getSavingsBalance();
		double checking = this.getCheckingBalance();
		double cd = this.getCDBalance();
		double combined = (savings + checking + cd);
		return combined;
	}
	
	public int compareTo(AccountHolder otherAccountHolder) {
		//When we extend the interface of Comparable, we have to 
		//override the compareTo method to return a 0 if parameters are equal,
		//a 1 if the original is greater than the passed in parameter, and a
		//-1 if the passed in parameter is greater.
		if (this.getCombinedBalance() == otherAccountHolder.getCombinedBalance()) {
			return 0;
		}
		else {
		return (this.getCombinedBalance() > otherAccountHolder.getCombinedBalance() ? 1 : -1);
		}
	}
	
	public String writeToString() {
		StringBuilder writeSB = new StringBuilder();
		writeSB.append(lastName).append(",").append(middleName).append(",").append(firstName).append(",").append(ssn);
		writeSB.append("\n").append(this.getNumberOfCheckingAccounts());
		for (int i = 0; i < this.getNumberOfCheckingAccounts(); i++) {
			writeSB.append("\n").append(this.checkingAccountArray[i].writeToString());
			}
		writeSB.append("\n").append(this.getNumberOfSavingsAccounts());
		for (int i = 0; i < this.getNumberOfSavingsAccounts(); i++) {
			writeSB.append("\n").append(this.savingsAccountArray[i].writeToString());
			}
		writeSB.append("\n").append(this.getNumberOfCDAccounts());
		for (int i = 0; i < this.getNumberOfCDAccounts(); i++) {
			writeSB.append("\n").append(this.cdAccountArray[i].writeToString());
			}
		return writeSB.toString();
	}
	
	public static AccountHolder readFromString(String accountHolderData) throws ParseException {
		String [] temp = accountHolderData.split(",");
		String parsedLast = temp[0];
		String parsedMiddle = temp[1];
		String parsedFirst = temp[2];
		String parsedSSN = temp[3];
		AccountHolder newAcct = new AccountHolder(parsedFirst, parsedMiddle, parsedLast, parsedSSN);
		return newAcct;
	}		
}