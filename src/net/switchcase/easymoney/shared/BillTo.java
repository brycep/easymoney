/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:32 PM
 */
@SuppressWarnings("serial")
public class BillTo implements Serializable {
	
	private String id;
	private String name;
    private int dayOfMonth;
    private Date nextDueDate;
    private MoneyTo amount = new MoneyTo();
    
    public BillTo() {}
    
    public String getId() {
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

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

	public Date getNextDueDate() {
		return nextDueDate;
	}


	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
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
}
