/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;
import java.util.List;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
public class BudgetTo implements Serializable {
	
	private String name;
    private List<IncomeTo> incomes;
    private List<BillTo> monthlyBills;
    private List<ExpenseCategoryTo> categories;
    private MoneyTo balance;
    private MoneyTo savings;
    private MoneyTo monthlySavings;
    private String sharedWith;
    private String owner;

    public BudgetTo() {}
    
    public String getName()  {
    	return name;
    }
    
    public void setName(String name)  {
    	this.name = name;
    }

    public List<IncomeTo> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<IncomeTo> incomes) {
        this.incomes = incomes;
    }

    public List<BillTo> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(List<BillTo> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    public List<ExpenseCategoryTo> getCategories() {
        return categories;
    }

    public void setCategories(List<ExpenseCategoryTo> category) {
        this.categories = category;
    }

    public MoneyTo getBalance() {
        return balance;
    }

    public void setBalance(MoneyTo balance) {
        this.balance = balance;
    }

    public MoneyTo getSavings() {
        return savings;
    }

    public void setSavings(MoneyTo savings) {
        this.savings = savings;
    }

    public MoneyTo getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(MoneyTo monthlySavings) {
        this.monthlySavings = monthlySavings;
    }
    
   	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public MoneyTo calculateExpenseTotal() {
		MoneyTo money = new MoneyTo();
		for(ExpenseCategoryTo category : categories)  {
			money.add(category.getAmount());
		}
		return money;
	}
	
	public MoneyTo calculateIncomeTotal()  {
		MoneyTo money = new MoneyTo();
		for(IncomeTo income : incomes)  {
			money.add(income.getAmount());
		}
		return money;
	}

	public MoneyTo calculateBillTotal()  {
		MoneyTo money = new MoneyTo();
		for(BillTo bill : monthlyBills)  {
			money.add(bill.getAmount());
		}
		return money;
	}
}
