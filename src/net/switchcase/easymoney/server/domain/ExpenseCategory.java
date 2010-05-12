/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import javax.jdo.annotations.PersistenceCapable;

import net.switchcase.easymoney.shared.Frequency;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
@PersistenceCapable
public class ExpenseCategory {

	private Long id;
    private String name;
    private int amountDollars;
    private int amountCents;
    private Frequency frequencyToRefresh;
    private boolean accumulating;

    private int balanceDollars;
    private int balanceCents;

    public ExpenseCategory()  {}

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

    public int getAmountDollars() {
        return amountDollars;
    }

    public void setAmount(int amountDollars) {
        this.amountDollars = amountDollars;
    }
    
    public int getAmountCents()  {
    	return amountCents;
    }
    
    public void setAmountCents(int amountCents)  {
    	this.amountCents = amountCents;
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

    public int getBalanceDollars() {
        return balanceDollars;
    }
    
    public void setBalanceDollars(int balanceDollars)  {
    	this.balanceDollars = balanceDollars;
    }
    
    public int getBalanceCents()  {
    	return balanceCents;
    }

    public void setBalanceCents(int balanceCents) {
        this.balanceCents = balanceCents;
    }
}
