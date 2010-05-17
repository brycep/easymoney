package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.common.ValueListBox;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.Frequency;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ExpenseModelAdapter extends BaseModelAdapter {

	private static final int NAME_COLUMN = 0;
	private static final int FREQUENCY_COLUMN = 1;
	private static final int ACCUMULATING_COLUMN = 2;
	private static final int AMOUNT_COLUMN = 3;
	private static final int BALANCE_COLUMN = 4;
		
	public void renderHeaderRow(FlexTable table)  {
		addHeaderLabel(table, "Name", NAME_COLUMN);
		addHeaderLabel(table, "Frequency", FREQUENCY_COLUMN);
		addHeaderLabel(table, "Accumulating", ACCUMULATING_COLUMN);
		addHeaderLabel(table, "Amount", AMOUNT_COLUMN);
		addHeaderLabel(table, "Balance", BALANCE_COLUMN);

	}

	public ModelObject convertRowToDataObject(Row row, FlexTable table) {
		ExpenseRow expenseRow = (ExpenseRow) row;
		ExpenseCategoryTo expenseCategory = (ExpenseCategoryTo) row.getData();
		expenseCategory.setName(expenseRow.getName().getValue());
		expenseCategory.setAccumulating(expenseRow.getAccumulating().getValue());
		expenseCategory.setAmount(expenseRow.getAmount().getMoneyValue());
		expenseCategory.setBalance(expenseRow.getBalance().getMoneyValue());
		expenseCategory.setFrequencyToRefresh(getSelectedFrequency(expenseRow));
		return expenseCategory;
	}

	public Row createRow(int rowIndex) {
		ExpenseRow row = new ExpenseRow(rowIndex);
		row.setAccumulating(new CheckBox());
		row.setAmount(new MoneyTextBox());
		row.setBalance(new MoneyTextBox());
		row.setFrequency(new ValueListBox());
		row.setName(new TextBox());
		return row;
	}

	public void renderRow(Row row, FlexTable table) {
		ExpenseRow expenseRow = (ExpenseRow) row;
		
		table.setWidget(row.getRowIndex(), NAME_COLUMN, (Widget)expenseRow.getName());
		table.setWidget(row.getRowIndex(), FREQUENCY_COLUMN, (Widget)expenseRow.getFrequency());
		table.setWidget(row.getRowIndex(), ACCUMULATING_COLUMN, (Widget)expenseRow.getAccumulating());
		table.setWidget(row.getRowIndex(), AMOUNT_COLUMN, (Widget)expenseRow.getAmount());
		table.setWidget(row.getRowIndex(), BALANCE_COLUMN, (Widget)expenseRow.getBalance());
	}

	protected Frequency getSelectedFrequency(ExpenseRow row)  {
		String frequencyValue = row.getFrequency().getSelected().getValue();
		return Frequency.valueOf(frequencyValue);
	}

	
}