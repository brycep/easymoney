package net.switchcase.easymoney.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class AccountTo implements Serializable  {

	private String id;
	private String name;
	private AccountType accountType;
	private MoneyTo balance;

	public AccountTo()  {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public MoneyTo getBalance() {
		return balance;
	}

	public void setBalance(MoneyTo balance) {
		this.balance = balance;
	}
	
	public int getBalanceAsInt()  {
		if (balance == null)  {
			return 0;
		}
		
		return balance.toInt();
	}
	
	public void setBalanceAsInt(int newBalance)  {
		this.balance = new MoneyTo(newBalance);
	}
	
	
}
