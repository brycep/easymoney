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

import net.switchcase.easymoney.shared.EnvelopeType;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:23 PM
 */
@PersistenceCapable
public class CashEnvelope {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;

	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long keyId;
	
	@Persistent private String name;
	@Persistent private Long amount = 0L;
	@Persistent private EnvelopeType type;

	@Persistent private long balance = 0L;
	
	@Persistent private Budget budget;

    public CashEnvelope()  {}
    

    public CashEnvelope(String name, EnvelopeType type,	long amount, long balance, Budget budget) {
		super();
		this.name = name;
		this.type = type;
		this.budget = budget;
		this.amount = amount;
		this.balance = balance;
	}


	public String getId()  {
    	return id;
    }
    
    public void setId(String id)  {
    	this.id = id;
    }
    
	public Long getKeyId()  {
		return keyId;
	}
	
	public void setKeyId(Long id)  {
		this.keyId = id;
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
    
	public EnvelopeType getType() {
		return type;
	}

	public void setType(EnvelopeType type) {
		this.type = type;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
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
		CashEnvelope other = (CashEnvelope) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void addBalance(long amount)  {
		balance += amount;
	}
	
	public void subtractBalance(long amount) throws InsufficientFundsException {
		if (0 > (balance - amount))  {
			throw new InsufficientFundsException();
		}
		balance -= amount;
	}
	    
}
