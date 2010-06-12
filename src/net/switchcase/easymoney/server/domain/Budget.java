/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import net.switchcase.easymoney.shared.DebitCreditType;

import com.google.appengine.api.users.User;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
@PersistenceCapable
public class Budget implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	@Persistent private String name;
	
	@Persistent(defaultFetchGroup="true")
	private List<Income> incomes = new ArrayList<Income>();
	
	@Persistent(defaultFetchGroup="true") 
	private List<Bill> monthlyBills = new ArrayList<Bill>();
	
	@Persistent(defaultFetchGroup="true", mappedBy="budget")
	private List<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();

	@Persistent private Account checkingAccount;
	@Persistent private Account savingsAccount;
	@Persistent private Account billsAccount;
	
	@Persistent private Long monthlySavings;
    @Persistent private User owner;
    @Persistent private String sharedWith;
    @Persistent private Date createDate;
    @Persistent private Date lastAccessed;

    public Budget() {}
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName()  {
    	return name;
    }
    
    public void setName(String name)  {
    	this.name = name;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Bill> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(List<Bill> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    public List<ExpenseCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ExpenseCategory> category) {
        this.categories = category;
    }

    public Long getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(Long monthlySavings) {
        this.monthlySavings = monthlySavings;
    }

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccess) {
		this.lastAccessed = lastAccess;
	}

	public Account getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(Account savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	public Account getBillsAccount() {
		return billsAccount;
	}

	public void setBillsAccount(Account billsAccount) {
		this.billsAccount = billsAccount;
	}

	public Account getCheckingAccount() {
		return checkingAccount;
	}

	public void setCheckingAccount(Account checkingAccount) {
		this.checkingAccount = checkingAccount;
	}
	
	public ExpenseCategory getExpenseCategory(String key)  {
		for(ExpenseCategory expenseCategory : categories)  {
			if (key.equals(expenseCategory.getId()) )  {
				return expenseCategory;
			}
		}
		return null;
	}

	public List<Account> getAccountList()  {
		List<Account> accountList = new ArrayList<Account>();
		
		for(ExpenseCategory expenseCategory : categories)  {
			if (null != expenseCategory.getAccount())  {
				accountList.add(expenseCategory.getAccount());
			}
		}
		
		if (savingsAccount != null)  {
			accountList.add(savingsAccount);
		}
		
		if (checkingAccount != null)  {
			accountList.add(checkingAccount);
		}
		
		if (billsAccount != null)  {
			accountList.add(billsAccount);
		}
		
		return accountList;
	}
	
	public Account findAccount(String key)  {
		Account account = null;
		for(Account item : getAccountList())  {
			if (item.getId().equals(key))  {
				account = item;
			}
		}
		return account;
	}
	
	public void processExpenseTransaction(Transaction transaction,
										  ExpenseCategory expenseCategory) throws InsufficientFundsException {

		transaction.setDebitAccountKey(expenseCategory.getAccount().getId());
		transaction.setCreditAccountKey(checkingAccount.getId());
		transaction.setExpenseCategoryKey(expenseCategory.getId());

		processTransaction(expenseCategory.getAccount(), checkingAccount, transaction.getAmount());

	}
	
	private void processTransaction(Account debitAccount, Account creditAccount, long amount) throws InsufficientFundsException {
		debitAccount.addDebit(amount);
		creditAccount.addCredit(amount);
		
	}
	
	public Transaction transfer(Account sourceAccount, Account destAccount, long amount) 
			throws InsufficientFundsException {
		Transaction txn = new Transaction();
		txn.setDescription("* Tranfer from " + sourceAccount.getName() + " to " + destAccount.getName() +
				" (System Generated Transaction)");
		txn.setReconsiled(true);
		txn.setCreateTimestamp(new Date());
		txn.setTransactionDate(new Date());
		txn.setAmount(amount);
		txn.setSource("SYSTEM");
		
		if (sourceAccount.getAccountType().equals(DebitCreditType.Debit))  {
			txn.setCreditAccountKey(sourceAccount.getId());
			txn.setDebitAccountKey(destAccount.getId());
			processTransaction(destAccount, sourceAccount, amount);
		} else  {
			txn.setDebitAccountKey(sourceAccount.getId());
			txn.setCreditAccountKey(destAccount.getId());
			processTransaction(sourceAccount, destAccount, amount);
		}
		return txn;
	}
	    
}
