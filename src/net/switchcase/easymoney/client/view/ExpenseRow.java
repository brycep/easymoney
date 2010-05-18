package net.switchcase.easymoney.client.view;

import java.util.Arrays;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.ListItem;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.Frequency;

import com.google.gwt.user.client.ui.HasValue;


public class ExpenseRow implements Row {

	private int rowIndex = 0;
	private ExpenseCategoryTo expenseCategoryTo;
	
	private HasValue<String> name;
	private HasValueList frequency;
	private HasValue<Boolean> accumulating;
	private HasMoneyValue amount;
	private HasMoneyValue balance;

	public ExpenseRow(int rowIndex)  {
		this.rowIndex = rowIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}

	public Object getData()  {
		return expenseCategoryTo;
	}
	
	public void updateModel() {
//		dataObject.setName()
	}

	public void setData(Object dataObject) {
		ExpenseCategoryTo category = (ExpenseCategoryTo) dataObject;
		name.setValue(category.getName());
		accumulating.setValue(category.isAccumulating());
		amount.setValue(category.getAmount());
		balance.setValue(category.getBalance());
		frequency.setList(Arrays.asList(
				new ListItem(Frequency.BiWeekly.toString(), "Bi-Weekly"),
				new ListItem(Frequency.Monthly.toString(), "Monthly"),
				new ListItem(Frequency.SemiMonthly.toString(), "Semi-Monthly"),
				new ListItem(Frequency.Weekly.toString(), "Weekly")
		));
		frequency.setSelected(category.getFrequencyToRefresh().toString());

	}

	public HasValue<String> getName() {
		return name;
	}

	public void setName(HasValue<String> name) {
		this.name = name;
	}

	public HasValueList getFrequency() {
		return frequency;
	}

	public void setFrequency(HasValueList frequency) {
		this.frequency = frequency;
	}

	public HasValue<Boolean> getAccumulating() {
		return accumulating;
	}

	public void setAccumulating(HasValue<Boolean> accumulating) {
		this.accumulating = accumulating;
	}

	public HasMoneyValue getAmount() {
		return amount;
	}

	public void setAmount(HasMoneyValue amount) {
		this.amount = amount;
	}

	public HasMoneyValue getBalance() {
		return balance;
	}

	public void setBalance(HasMoneyValue balance) {
		this.balance = balance;
	}

	

}
