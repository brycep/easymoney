/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import net.switchcase.easymoney.client.common.ModelObject;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
@SuppressWarnings("serial")
public class ExpenseCategoryTo implements ModelObject {

	private Long id;
    private String name;
    private MoneyTo amount = new MoneyTo();
    private Frequency frequencyToRefresh = Frequency.Monthly;
    private boolean accumulating;

    private MoneyTo balance = new MoneyTo();

    public ExpenseCategoryTo()  {}

    public Long getId()  {
    	return id;
    }
    
    public void setId(Long id)  {
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
}
