package net.switchcase.easymoney.client.view;

import java.util.Date;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.shared.BillTo;

import com.google.gwt.user.client.ui.HasValue;

public class BillRow implements Row  {
	
	private int rowIndex = 0;
	private BillTo bill;
	
	private HasValue<String> name;
	private HasValueList billDueDay;
	private HasValue<Date> nextDueDate;
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
		bill.setNextDueDate(nextDueDate.getValue());
		bill.setDayOfMonth(Integer.parseInt(billDueDay.getSelected().getValue()));
	}

	public void setData(Object dataObject) {
		this.bill = (BillTo) dataObject;
		name.setValue(bill.getName());
		nextDueDate.setValue(bill.getNextDueDate());
		billDueDay.setSelected(Integer.toString(bill.getDayOfMonth()));
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

	public HasValueList getBillDueDay() {
		return billDueDay;
	}

	public void setBillDueDay(HasValueList billDueDay) {
		this.billDueDay = billDueDay;
	}

	public HasMoneyValue getAmount() {
		return amount;
	}

	public void setAmount(HasMoneyValue amount) {
		this.amount = amount;
	}

	public HasValue<Date> getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(HasValue<Date> nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	
	
}
