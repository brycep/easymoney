/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import com.google.gwt.i18n.client.NumberFormat;

/**  This class represents money
 *
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:40 PM
 */
public class Money {

    private int dollars = 0;
    private int cents = 0;

    public Money() {}

    public Money(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
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
    
	public String toString()  {
		NumberFormat nformat = NumberFormat.getFormat("$ 0.00");
		double value = (double) dollars + ((double)cents / 100.00);
		return nformat.format(value);
	}
	
	/** This object is on the left side of the equation 
	 * and the incoming object will be on the right, i.e.
	 * leftValue - rightValue = result
	 * @param money
	 * @return A money object representing the result.
	 */
	public void subtract(Money money)  {
		Integer leftValue = (dollars * 100) + cents;
		Integer rightValue = (money.getDollars() * 100) + money.getCents();
		this.setValue(leftValue - rightValue);
	}
	
	public void add(int value)  {
		dollars = dollars + value;
	}
	
	public void add(Money moneyValue)   {
		Integer currentValue = this.toInt();
		currentValue = currentValue + moneyValue.toInt();
		this.setValue(currentValue);
	}
	
	public Integer toInt()  {
		return (dollars * 100) + cents;
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

}
