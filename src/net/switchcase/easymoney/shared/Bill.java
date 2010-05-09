/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:17:32 PM
 */
public class Bill {
    private boolean reminderActive;
    private int dayOfMonth;
    private int reminderDay;

    public Bill() {}

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
}
