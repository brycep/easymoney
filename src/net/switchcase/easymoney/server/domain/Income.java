/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long keyId;
	
	@Persistent private String name;
	@Persistent private Long amount;
	@Persistent private Frequency frequency;
	@Persistent private Date nextPayDate;
	
    public Income() {}
    
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public Long getKeyId()  {
		return keyId;
	}
	
	public void setKeyId(Long id)  {
		this.keyId = id;
	}
	
	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
		Income other = (Income) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
	public void moveToNextPayDate()  {
		if (Frequency.BiWeekly.equals(frequency))  {
			Calendar cal = Calendar.getInstance();
			cal.setTime(nextPayDate);
			cal.add(Calendar.DATE, 14);
			nextPayDate = cal.getTime();
		}
		
	}
    
	public boolean isPaidToday(Date now)  {
		if (null == nextPayDate)  {
			return false;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		return format.format(now).equals(format.format(this.nextPayDate)); 
	}
}
