package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.MoneyLabel;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.event.RowValueChangeHandler;
import net.switchcase.easymoney.shared.CashEnvelopeTo;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ExpenseModelAdapter extends BaseModelAdapter {

	private static final int NAME_COLUMN = 0;
	private static final int AMOUNT_COLUMN = 1;
	private static final int BALANCE_COLUMN = 2;
		
	public void renderHeaderRow(FlexTable table)  {
		addHeaderLabel(table, "Name", NAME_COLUMN);
		addHeaderLabel(table, "Amount", AMOUNT_COLUMN);
		addHeaderLabel(table, "Balance", BALANCE_COLUMN);

		table.getCellFormatter().addStyleName(0, BALANCE_COLUMN, "right-align");
		table.getCellFormatter().addStyleName(0, AMOUNT_COLUMN, "right-align");

	}

	public Object convertRowToDataObject(Row row, FlexTable table) {
		ExpenseRow expenseRow = (ExpenseRow) row;
		CashEnvelopeTo expense = (CashEnvelopeTo) row.getData();
		expense.setName(expenseRow.getName().getValue());
		expense.setAmount(expenseRow.getAmount().getMoneyValue());
		expense.setBalance(expenseRow.getBalance().getMoneyValue());
		return expense;
	}
	
	public Row createRow(int rowIndex, final RowValueChangeHandler dataTable) {
		final ExpenseRow row = new ExpenseRow(rowIndex);
		row.setBalance(new MoneyLabel());
		row.setName(new TextBox());
		
		MoneyTextBox amount = new MoneyTextBox();
		amount.addValueChangeHandler(new ValueChangeHandler<String>()  {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				dataTable.onRowValueChanged(row);
			}
		});
		
		row.setAmount(amount);
			
		return row;
	}
	
	public void renderRow(Row row, FlexTable table) {
		ExpenseRow expenseRow = (ExpenseRow) row;
		
		table.setWidget(row.getRowIndex(), NAME_COLUMN, (Widget)expenseRow.getName());
		table.setWidget(row.getRowIndex(), AMOUNT_COLUMN, (Widget)expenseRow.getAmount());
		table.setWidget(row.getRowIndex(), BALANCE_COLUMN, (Widget)expenseRow.getBalance());
	}

	
}