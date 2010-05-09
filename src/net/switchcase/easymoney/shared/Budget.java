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
public class Budget implements Serializable {
    private List<Income> incomes;
    private List<Bill> monthlyBills;
    private List<ExpenseCategory> category;
    private Money balance;
    private Money savings;
    private Money monthlySavings;

    public Budget() {}

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

    public List<ExpenseCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ExpenseCategory> category) {
        this.category = category;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getSavings() {
        return savings;
    }

    public void setSavings(Money savings) {
        this.savings = savings;
    }

    public Money getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(Money monthlySavings) {
        this.monthlySavings = monthlySavings;
    }
}
