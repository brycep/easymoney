package net.switchcase.easymoney.shared;


public class AccountTo {

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
	
	
}
