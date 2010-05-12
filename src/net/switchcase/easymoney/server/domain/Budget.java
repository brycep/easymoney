/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.MoneyTo;

import com.google.appengine.api.datastore.Key;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
@PersistenceCapable
public class Budget implements Serializable {
	
	private Key id;
	private String name;
    private List<Income> incomes;
    private List<BillTo> monthlyBills;
    private List<ExpenseCategory> categories;
    private MoneyTo balance;
    private MoneyTo savings;
    private MoneyTo monthlySavings;

    public Budget() {}
    
    public Key getId() {
		return id;
	}

	public void setId(Key id) {
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

    public List<BillTo> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(List<BillTo> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    public List<ExpenseCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ExpenseCategory> category) {
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
}
