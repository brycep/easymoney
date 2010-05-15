package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.Frequency;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

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

	public ModelObject convertRowToDataObject(int row, ModelObject dataObject, FlexTable table) {
		return null;
	}

	public void renderRow(int row, ModelObject dataObject, FlexTable table) {
		
		TextBox name = new TextBox();
		ListBox frequency = new ListBox();
		CheckBox accumulating = new CheckBox();
		TextBox amount = new TextBox();
		TextBox balance = new TextBox();
		
		populateFrequency(frequency);
		
		if (dataObject instanceof ExpenseCategoryTo)  {
			ExpenseCategoryTo category = (ExpenseCategoryTo) dataObject;
			if (null != category.getName())  {
				name.setValue(category.getName());
			}
			if (category.isAccumulating()) {
				accumulating.setValue(true);
			}
			if (null != category.getAmount())  {
				amount.setValue(category.getAmount().toString());
			}
			if (null != category.getBalance())  {
				amount.setValue(category.getBalance().toString());
			}
			
			setSelectedFrequency(category.getFrequencyToRefresh(), frequency);
		}
		
		table.setWidget(row, NAME_COLUMN, name);
		table.setWidget(row, FREQUENCY_COLUMN, frequency);
		table.setWidget(row, ACCUMULATING_COLUMN, accumulating);
		table.setWidget(row, AMOUNT_COLUMN, amount);
		table.setWidget(row, BALANCE_COLUMN, balance);

	}
	
}