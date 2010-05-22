package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.shared.BillTo;

import com.google.gwt.user.client.ui.HasValue;

public class BillRow implements Row  {
	
	private int rowIndex = 0;
	private BillTo bill;
	
	private HasValue<String> name;
	private HasValue<Boolean> reminder;
	private HasValueList billDueDay;
	private HasValueList reminderDay;
	private HasMoneyValue amount;
	
	public BillRow(int rowIndex)  {
		this.rowIndex = rowIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Object getData() {
		return bill;
	}
	
	public void updateModel() {
		bill.setName(name.getValue());
		bill.setAmount(amount.getMoneyValue());
		bill.setDayOfMonth(Integer.parseInt(billDueDay.getSelected().getValue()));
		bill.setReminderActive(reminder.getValue());
		bill.setReminderDay(Integer.parseInt(reminderDay.getSelected().getValue()));
	}

	public void setData(Object dataObject) {
		this.bill = (BillTo) dataObject;
		name.setValue(bill.getName());
		reminder.setValue(bill.isReminderActive());
		billDueDay.setSelected(Integer.toString(bill.getDayOfMonth()));
		reminderDay.setSelected(Integer.toString(bill.getReminderDay()));
		if (null != bill.getAmount())  {
			amount.setValue(bill.getAmount());
		}
		
	}

	public HasValue<String> getName() {
		return name;
	}

	public void setName(HasValue<String> name) {
		this.name = name;
	}

	public HasValue<Boolean> getReminder() {
		return reminder;
	}

	public void setReminder(HasValue<Boolean> reminder) {
		this.reminder = reminder;
	}

	public HasValueList getBillDueDay() {
		return billDueDay;
	}

	public void setBillDueDay(HasValueList billDueDay) {
		this.billDueDay = billDueDay;
	}

	public HasValueList getReminderDay() {
		return reminderDay;
	}

	public void setReminderDay(HasValueList reminderDay) {
		this.reminderDay = reminderDay;
	}

	public HasMoneyValue getAmount() {
		return amount;
	}

	public void setAmount(HasMoneyValue amount) {
		this.amount = amount;
	}
	
}
