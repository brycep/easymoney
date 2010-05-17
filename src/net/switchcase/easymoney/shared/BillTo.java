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
 * Time: 7:17:32 PM
 */
@SuppressWarnings("serial")
public class BillTo implements ModelObject {
	
	private Long id;
	private String name;
    private boolean reminderActive;
    private int dayOfMonth;
    private int reminderDay;
    private MoneyTo amount;

    public BillTo() {}

    
    public Long getId() {
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


	public boolean isReminderActive() {
        return reminderActive;
    }

    public void setReminderActive(boolean reminderActive) {
        this.reminderActive = reminderActive;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getReminderDay() {
        return reminderDay;
    }

    public void setReminderDay(int reminderDay) {
        this.reminderDay = reminderDay;
    }


	public MoneyTo getAmount() {
		return amount;
	}


	public void setAmount(MoneyTo amount) {
		this.amount = amount;
	}
    
    
}
