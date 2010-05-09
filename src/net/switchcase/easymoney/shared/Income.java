/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.util.Date;

/**  This is a source of income.  It specifies when
 * the user gets paid and how much.
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:11 PM
 */
public class Income {

    private String name;
    private Money amount;
    private Frequency frequency;
    private Date nextPayDate;

    public Income() {}

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
