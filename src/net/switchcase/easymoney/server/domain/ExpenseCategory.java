/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import net.switchcase.easymoney.shared.Frequency;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
@PersistenceCapable
public class ExpenseCategory {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	@Persistent private String name;
	@Persistent private Long amount;
	@Persistent private Frequency frequencyToRefresh;
	@Persistent private boolean accumulating;

	@Persistent private Long balance;
	
	@Persistent private Budget budget;

    public ExpenseCategory()  {}

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	public Budget getBudget()  {
		return budget;
	}
	
	public void setBudget(Budget budget)  {
		this.budget = budget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseCategory other = (ExpenseCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void subtractFromBalance(long amount)  {
		balance -= amount;
		budget.setBalance(budget.getBalance() - amount);
	}
    
}
