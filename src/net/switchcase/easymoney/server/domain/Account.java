package net.switchcase.easymoney.server.domain;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import net.switchcase.easymoney.shared.AccountType;
import net.switchcase.easymoney.shared.DebitCreditType;

@PersistenceCapable
public class Account {
		
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	@Persistent private String name;
	@Persistent private AccountType accountType;
	@Persistent private long balance;
	@Persistent private Budget budget;
	
	public Account()  {
	}
	
	public Account(String id, String name, AccountType accountType, long balance, Budget budget)  {
		this(name, accountType, balance, budget);
		this.id = id;
	}	
	
	public Account(String name, AccountType accountType, long balance, Budget budget)  {
		this.name = name;
		this.accountType = accountType;
		this.balance = balance;
		this.budget = budget;
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

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	
	public void addDebit(long amount) throws InsufficientFundsException {
		if (accountType.getDebitCredit().equals(DebitCreditType.Debit))  {
			balance += amount;
		} else  {
			checkSufficientFunds(amount);
			balance -= amount;
		}
	}
	
	public void addCredit(long amount) throws InsufficientFundsException {
		if (accountType.getDebitCredit().equals(DebitCreditType.Debit)) {
			checkSufficientFunds(amount);
			balance -= amount;
		} else  {
			balance += amount;
		}
	}
	
	private void checkSufficientFunds(Long amount) throws InsufficientFundsException {
		if (0 > (balance - amount))  {
			throw new InsufficientFundsException();
		}
	}


}
