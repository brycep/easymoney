/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

/**  This class represents money
 *
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:40 PM
 */
public class Money {

    private long dollars = 0;
    private long cents = 0;

    public Money() {}

    public Money(long dollars, long cents) {
        this.dollars = dollars;
        this.cents = cents;
    }

    public long getDollars() {
        return dollars;
    }

    public void setDollars(long dollars) {
        this.dollars = dollars;
    }

    public long getCents() {
        return cents;
    }

    public void setCents(long cents) {
        this.cents = cents;
    }
}
