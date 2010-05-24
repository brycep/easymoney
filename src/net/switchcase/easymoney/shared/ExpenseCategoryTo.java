/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
@SuppressWarnings("serial")
public class ExpenseCategoryTo implements Serializable {

	private String id;
    private String name;
    private MoneyTo amount = new MoneyTo();
    private Frequency frequencyToRefresh = Frequency.Monthly;
    private boolean accumulating;

    private MoneyTo balance = new MoneyTo();

    public ExpenseCategoryTo()  {}

    public String getId()  {
    	return id;
    }
    
    public void setId(String id)  {
    	this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoneyTo getAmount() {
        return amount;
    }

    public void setAmount(MoneyTo amount) {
        this.amount = amount;
    }
    
    public Integer getAmountAsInt()  {
    	if (null == amount)  {
    		return 0;
    	}
    	return amount.toInt();
    }
    
    public void setAmountAsInt(Integer value)  {
    	amount = new MoneyTo(value);
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

    public MoneyTo getBalance() {
        return balance;
    }

    public void setBalance(MoneyTo balance) {
        this.balance = balance;
    }
    
    public Integer getBalanceAsInt()  {
    	if (balance == null)  {
    		return 0;
    	}
    	return balance.toInt();
    }
    
    public void setBalanceAsInt(Integer value)  {
    	balance = new MoneyTo(value);
    }
}
