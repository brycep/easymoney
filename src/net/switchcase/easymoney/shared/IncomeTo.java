/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.util.Date;

import net.switchcase.easymoney.client.common.ModelObject;

/**  This is a source of income.  It specifies when
 * the user gets paid and how much.
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:11 PM
 */
@SuppressWarnings("serial")
public class IncomeTo implements ModelObject {

	private Long id;
    private String name;
    private MoneyTo amount;
    private Frequency frequency;
    private Date nextPayDate;

    public IncomeTo() {}

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
