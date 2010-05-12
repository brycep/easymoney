/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

import net.switchcase.easymoney.shared.Frequency;

/**  This is a source of income.  It specifies when
 * the user gets paid and how much.
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:11 PM
 */
@PersistenceCapable
public class Income {

	private long id;
    private String name;
    private int amountDollars;
    private int amountCents;
    private Frequency frequency;
    private Date nextPayDate;

    public Income() {}

    public long getId()  {
    	return id;
    }
    
    public void setId(long id)  {
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

	public void setAmountDollars(int amountDollars) {
		this.amountDollars = amountDollars;
	}

	public int getAmountCents() {
		return amountCents;
	}

	public void setAmountCents(int amountCents) {
		this.amountCents = amountCents;
	}

	public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Date getNextPayDate() {
        return nextPayDate;
    }

    public void setNextPayDate(Date nextPayDate) {
        this.nextPayDate = nextPayDate;
    }
}
