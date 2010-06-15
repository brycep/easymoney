package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.shared.CashEnvelopeTo;

import com.google.gwt.user.client.ui.HasValue;


public class ExpenseRow implements Row {

	private int rowIndex = 0;
	private CashEnvelopeTo expenseCategoryTo;
	
	private HasValue<String> name;
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
		expenseCategoryTo.setName(name.getValue());
		expenseCategoryTo.setAmount(amount.getMoneyValue());
	}

	public void setData(Object dataObject) {
		CashEnvelopeTo category = (CashEnvelopeTo) dataObject;
		name.setValue(category.getName());
		amount.setValue(category.getAmount());
		balance.setValue(category.getBalance());
		this.expenseCategoryTo = category;
	}

	public HasValue<String> getName() {
		return name;
	}

	public void setName(HasValue<String> name) {
		this.name = name;
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
