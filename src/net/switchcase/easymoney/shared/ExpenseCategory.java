/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
public class ExpenseCategory {

    private String name;
    private Money amount;
    private Frequency frequencyToRefresh;
    private boolean accumulating;

    private Money balance;

    public ExpenseCategory()  {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Frequency getFrequencyToRefresh() {
        return frequencyToRefresh;
    }

    public void setFrequencyToRefresh(Frequency frequencyToRefresh) {
        this.frequencyToRefresh = frequencyToRefresh;
    }

    public boolean isAccumulating() {
        return accumulating;
    }

    public void setAccumulating(boolean accumulating) {
        this.accumulating = accumulating;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
