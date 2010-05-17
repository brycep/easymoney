/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;

/**  This class represents money
 *
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:40 PM
 */
@SuppressWarnings("serial")
public class MoneyTo implements Serializable {

    private int dollars = 0;
    private int cents = 0;

    public MoneyTo() {}

    public MoneyTo(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
    }
    
    public MoneyTo(double value)  {
    	setDoubleValue(value);
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }
    	
	/** This object is on the left side of the equation 
	 * and the incoming object will be on the right, i.e.
	 * leftValue - rightValue = result
	 * @param money
	 * @return A money object representing the result.
	 */
	public void subtract(MoneyTo money)  {
		Integer leftValue = (dollars * 100) + cents;
		Integer rightValue = (money.getDollars() * 100) + money.getCents();
		this.setValue(leftValue - rightValue);
	}
	
	public void add(int value)  {
		dollars = dollars + value;
	}
	
	public void add(MoneyTo moneyValue)   {
		Integer currentValue = this.toInt();
		currentValue = currentValue + moneyValue.toInt();
		this.setValue(currentValue);
	}
	
	public Integer toInt()  {
		return (dollars * 100) + cents;
	}
	
	public void setDoubleValue(double value)  {
		this.setValue((int)value * 100);
	}
	
	public void setValue(Integer value)   {
		if (value == 0)  { 
			this.dollars = 0;
			this.cents = 0;
		} else  {
			this.dollars = value / 100;
			this.cents = Math.abs(value % 100);
		}
	}
	
	public String formatCentsAsString()  {
		if (cents < 10)  {
			return "0" + ((Integer)cents).toString();
		} else  {
			return ((Integer)cents).toString();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cents;
		result = prime * result + dollars;
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
		MoneyTo other = (MoneyTo) obj;
		if (cents != other.cents)
			return false;
		if (dollars != other.dollars)
			return false;
		return true;
	}

}
