/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;
import java.util.Date;

/**  This is a source of income.  It specifies when
 * the user gets paid and how much.
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:11 PM
 */
@SuppressWarnings("serial")
public class IncomeTo implements Serializable {

	private String id;
    private String name;
    private MoneyTo amount = new MoneyTo();
    private Frequency frequency = Frequency.BiWeekly;
    private Date nextPayDate;
    
	private AccountTo debitAccount; 
	private AccountTo creditAccount; 

    public IncomeTo() {}

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

	public AccountTo getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(AccountTo debitAccount) {
		this.debitAccount = debitAccount;
	}

	public AccountTo getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(AccountTo creditAccount) {
		this.creditAccount = creditAccount;
	}
    
}
