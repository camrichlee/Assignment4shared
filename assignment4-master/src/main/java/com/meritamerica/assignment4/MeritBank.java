package com.meritamerica.assignment4;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class MeritBank {
	private static ArrayList<AccountHolder> holderArray = new ArrayList<AccountHolder>(1);
	private static ArrayList<CDOffering> cdOfferingArray = new ArrayList<CDOffering>(1);
	private static FraudQueue fraudQueue = new FraudQueue();
	public static long accountNumber;
	
	static void addAccountHolder(AccountHolder accountHolder) {
		holderArray.add(accountHolder);
	}
	
	static ArrayList<AccountHolder> getAccountHolders() {
		return holderArray;
	}
	
	static ArrayList<CDOffering> getCDOfferings() {
		return cdOfferingArray;
	}
	
	static CDOffering getBestCDOfferings(double depositAmount) {
		int bestCounter = 0;
		for (int i = 0; i < cdOfferingArray.size(); i++) {
			CDAccount temp1 = new CDAccount(cdOfferingArray.get(i), depositAmount);
			CDAccount temp2 = new CDAccount(cdOfferingArray.get(i + 1), depositAmount);
			if(temp2.futureValue() > temp1.futureValue()) {
			bestCounter = (i + 1);
			}
		}
		return cdOfferingArray.get(bestCounter);
	}
	
	static CDOffering getSecondBestCDOfferings(double depositAmount) {
		int bestCounter = 0;
		for (int i = 0; i < cdOfferingArray.size(); i++) {
			CDAccount temp1 = new CDAccount(cdOfferingArray.get(i), depositAmount);
			CDAccount temp2 = new CDAccount(cdOfferingArray.get(i + 1), depositAmount);
			if(temp2.futureValue() > temp1.futureValue()) {
			bestCounter = (i + 1);
			}
		}
		
		int secondCounter = 0;
		for (int i = 0; i < cdOfferingArray.size(); i++) {
			CDAccount bestTemp = new CDAccount(cdOfferingArray.get(bestCounter), depositAmount);
			CDAccount temp1 = new CDAccount(cdOfferingArray.get(i), depositAmount);
			CDAccount temp2 = new CDAccount(cdOfferingArray.get(i + 1), depositAmount);
			if((temp2.futureValue() > temp1.futureValue()) &&
					bestTemp.futureValue() > temp2.futureValue()) {
			secondCounter = i + 1;
			}
		}
		return cdOfferingArray.get(secondCounter);
	}
	
	static void clearCDOfferings() {
		cdOfferingArray = null;
	}
	
	static void setCDOfferings(CDOffering[] offerings) {
			List<CDOffering> temp = Arrays.asList(offerings);
			cdOfferingArray = (ArrayList<CDOffering>) temp;		
	}
	
	static long getNextAccountNumber() {
		return accountNumber;
	}
	
	static double totalBalances() {
		return holderArray.get((int) accountNumber).getCombinedBalance();
	}
	
	static double futureValue(double presentValue, double interestRate, int term) {
		return recursiveFutureValue(presentValue, interestRate, term);
	}
	
	static double recursiveFutureValue(double presentValue, double interestRate, int term) {
		double futureValue = 0;
		if (term > 0) { 
			futureValue = recursiveFutureValue((presentValue + (presentValue * interestRate)), interestRate, term - 1);
		}
		return futureValue;
	}
	
	public static boolean processTransaction(Transaction tran) throws NegativeAmountException,
																	  ExceedsAvailableBalanceException,
																	  ExceedsFraudSuspicionLimitException {
		//TODO: remake this into sub-methods.
		//Chris's method is repeated, clunky, and doesn't work.
		//Always eliminate repetition. 
		BankAccount source = tran.getSourceAccount();
		BankAccount target = tran.getTargetAccount();
		if(source == null) {
			if(tran instanceof WithdrawTransaction) {
				if(tran.getAmount() < 0) {
					throw new NegativeAmountException("Can not withdraw a negative amount");
				}
				if(tran.getAmount() + target.getBalance() < 0) {
					throw new ExceedsAvailableBalanceException("Insufficient Balance");
				}
				if(tran.getAmount() < -1000) {
					fraudQueue.addTransaction(tran);
					throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
				}
				return true;
			}
			if(tran.getAmount() < 0) {
				throw new NegativeAmountException("Can not withdraw a negative amount");
			}
			if(tran.getAmount() > 1000) {
				fraudQueue.addTransaction(tran);
				throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
			}
			return true;
		}
		if(source.getBalance() < tran.getAmount()) {
			throw new ExceedsAvailableBalanceException("Insufficient Balance");
		}
		if(tran.getAmount() < 0) {
			throw new NegativeAmountException("Can not withdraw a negative amount");
		}
		if(tran.getAmount() > 1000) {
			fraudQueue.addTransaction(tran);
			throw new ExceedsFraudSuspicionLimitException("Transaction exceeds $1000.00 and must be reviewed prior to processing");
		}
		return true;
	}
	
	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	
	public static BankAccount getBankAccount(long accountID) {
		for (AccountHolder x: holderArray) {
			if (x.getCheckingAccounts().length != null) {
			return x.getCheckingAccounts().length;
			}
			if (x.getSavingsAccounts().length != null) {
			return x.getSavingsAccounts().length;
			}
			if (x.getCDAccounts().length != null) {
			return x.getCDAccounts().length;
			}
	}
		return null;
	}
	
	static void clearMemory() {
		holderArray = new ArrayList<AccountHolder>(1);
		cdOfferingArray = new ArrayList<CDOffering>(1);
	}
	
	//TODO Rewrite to read bank account transactions and the fraudQueue
	static boolean readFromFile(String fileName) {
		clearMemory();
		fraudQueue = new FraudQueue();
		Set<String> transactions = new HashSet<String>();
		try(BufferedReader nextLine = new BufferedReader(new FileReader(fileName))) {
			setNextAccountNumber(Long.valueOf(nextLine.readLine()));
			int numberOfCDOfferings = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i < numberOfCDOfferings; i++) {
				cdOfferingArray.add(CDOffering.readFromString(nextLine.readLine()));
			}
			int numberOfAccountHolders = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i < numberOfAccountHolders; i++) {
				AccountHolder nextAccountHolder = AccountHolder.readFromString(nextLine.readLine());
				MeritBank.addAccountHolder(nextAccountHolder);	
				int numberOfCheckingAccounts = Integer.valueOf(nextLine.readLine());
				for(int c = 0; c < numberOfCheckingAccounts; c++) {
					nextAccountHolder.addCheckingAccount(CheckingAccount.readFromString(nextLine.readLine()));
					int numberOfCheckingTransactions = Integer.valueOf(nextLine.readLine());
					for(int ct = 0; ct < numberOfCheckingTransactions; ct++) {	
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfSavingsAccounts = Integer.valueOf(nextLine.readLine());
				for(int s = 0; s < numberOfSavingsAccounts; s++) {
					nextAccountHolder.addSavingsAccount(SavingsAccount.readFromString(nextLine.readLine()));
					int numberOfSavingsTransactions = Integer.valueOf(nextLine.readLine());
					for(int st = 0; st < numberOfSavingsTransactions; st++) {
						transactions.add(nextLine.readLine());
					}
				}
				int numberOfCDAccounts = Integer.valueOf(nextLine.readLine());
				for(int cd = 0; cd < numberOfCDAccounts; cd++) {
					nextAccountHolder.addCDAccount(CDAccount.readFromString(nextLine.readLine()));
					int numberCDTransactions = Integer.valueOf(nextLine.readLine());
					for(int cdt = 0; cdt < numberCDTransactions; cdt++) {
						transactions.add(nextLine.readLine());
					}
				}
					
			}
			int numberOfFraudQueueTransactions = Integer.valueOf(nextLine.readLine());
			for(int fqt = 0; fqt < numberOfFraudQueueTransactions; fqt++) {
				fraudQueue.addTransaction(Transaction.readFromString(nextLine.readLine()));
			}
			System.out.println(transactions.size());
			for(String transaction : transactions) {
				Transaction newTran = Transaction.readFromString(transaction);
				if(newTran.getSourceAccount() == null) {
					newTran.getTargetAccount().addTransaction(newTran);
				}
				else {
					newTran.getTargetAccount().addTransaction(newTran);
					newTran.getSourceAccount().addTransaction(newTran);
				}
			}
			return true;
		}
		catch(Exception exception) {
			return false;
		}
	}
	
	//TODO should write bank account transactions and the fraud Queue 
	static boolean writeToFile(String fileName) throws IOException {
		try (BufferedWriter nextLine = new BufferedWriter(new FileWriter(fileName))){
        	nextLine.write(String.valueOf(accountNumber));
        	nextLine.newLine();
        	nextLine.write(String.valueOf(cdOfferingArray.size()));
        	nextLine.newLine();
        	for(int i = 0; i < cdOfferingArray.size(); i++) {
        		nextLine.write(cdOfferingArray.get(i).writeToString());
            	nextLine.newLine();
        	}
        	nextLine.write(String.valueOf(holderArray.size()));
        	nextLine.newLine();
        	for(int i = 0; i < holderArray.size(); i++) {
        		nextLine.write(holderArray.get(i).writeToString());
        		nextLine.newLine();
        		nextLine.write(String.valueOf(holderArray.get(i).getNumberOfCheckingAccounts()));
        		nextLine.newLine();
        		for(int c = 0; c< holderArray.get(i).getNumberOfCheckingAccounts(); c++ ) {
        			nextLine.write(holderArray.get(i).getCheckingAccounts().length.writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(holderArray.get(i).getCheckingAccounts().get(c).getTransactions().size()));
        			nextLine.newLine();
        			int ctl = holderArray.get(i).getCheckingAccounts().get(c).getTransactions().size();
        			for(int ct = 0; ct < ctl; ct++) {
        				nextLine.write(holderArray.get(i).getCheckingAccounts().get(c).getTransactions().get(ct).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(holderArray.get(i).getSavingsAccounts().size()));
        		nextLine.newLine();
        		for(int s = 0; s< holderArray.get(i).getNumberOfSavingsAccounts(); s++ ) {
        			nextLine.write(holderArray.get(i).getSavingsAccounts().get(s).writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(holderArray.get(i).getSavingsAccounts().get(s).getTransactions().size()));
        			nextLine.newLine();
        			int stl = holderArray.get(i).getSavingsAccounts().get(s).getTransactions().size();
        			for(int st = 0; st < stl; st++) {
        				nextLine.write(holderArray.get(i).getSavingsAccounts().get(s).getTransactions().get(st).writeToString());
        				nextLine.newLine();
        			}
        		}
        		nextLine.write(String.valueOf(holderArray.get(i).getCDAccounts().size()));
        		nextLine.newLine();
        		for(int cd = 0; cd< holderArray.get(i).getNumberOfCDAccounts(); cd++ ) {
        			nextLine.write(holderArray.get(i).getCDAccounts().get(cd).writeToString());
        			nextLine.newLine();
        			nextLine.write(String.valueOf(holderArray.get(i).getCDAccounts().get(cd).getTransactions().size()));
        			nextLine.newLine();
        			int cdtl = holderArray.get(i).getCDAccounts().get(cd).getTransactions().size();
        			for(int cdt = 0; cdt < cdtl; cdt++) {
        				nextLine.write(holderArray.get(i).getCDAccounts().get(cd).getTransactions().get(cdt).writeToString());
        				nextLine.newLine();
        			}
        		}
        	}
        	nextLine.write(String.valueOf(fraudQueue.getSize()));
        	nextLine.newLine();
        	FraudNode temp = fraudQueue.getHead();
        	while (temp.next != null) {
        		nextLine.write(fraudQueue.getTransaction().writeToString());
        		nextLine.newLine();
        	}
        	return true;
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	static ArrayList<AccountHolder> sortAccountHolders() {
		int i = 0;
		while (holderArray.get(i) != null) {
			i++;
		}
		Collections.sort(holderArray);
		return holderArray;
	}
	static void setNextAccountNumber(long nextAccountNumber) {
		MeritBank.accountNumber = nextAccountNumber;
	}
}