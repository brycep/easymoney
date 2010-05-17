package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.common.ValueListBox;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class IncomeModelAdapter extends BaseModelAdapter {

	private static final int NAME_COLUMN = 0;
	private static final int FREQUENCY_COLUMN = 1;
	private static final int NEXT_DATE_COLUMN = 2;
	private static final int AMOUNT_COLUMN = 3;
	
	public void renderHeaderRow(FlexTable table)  {
		addHeaderLabel(table, "Name", NAME_COLUMN);
		addHeaderLabel(table, "Frequency", FREQUENCY_COLUMN);
		addHeaderLabel(table, "Next Date", NEXT_DATE_COLUMN);
		addHeaderLabel(table, "Amount", AMOUNT_COLUMN);
	}
	
	public ModelObject convertRowToDataObject(Row row, FlexTable table) {
		IncomeTo incomeTo = (IncomeTo) row.getData();
		IncomeRow incomeRow = (IncomeRow) row;
		incomeTo.setAmount(incomeRow.getAmount().getMoneyValue());
		incomeTo.setFrequency(getSelectedFrequency(incomeRow));
		incomeTo.setName(incomeRow.getName().getValue());
		incomeTo.setNextPayDate(incomeRow.getNextDate().getValue());
		return incomeTo;
	}
	
	public Row createRow(int rowIndex)  {
		IncomeRow row = new IncomeRow(rowIndex);
		
		row.setName(new TextBox());
		row.setAmount(new MoneyTextBox());
		row.setFrequency(new ValueListBox());
		DateBox nextDate = new DateBox();
		nextDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		row.setNextDate(nextDate);

		return row;
	}

	public void renderRow(Row row, FlexTable table) {
		
		IncomeRow incomeRow = (IncomeRow) row;
		
		table.setWidget(row.getRowIndex(), NAME_COLUMN, (Widget)incomeRow.getName());
		table.setWidget(row.getRowIndex(), FREQUENCY_COLUMN, (Widget)incomeRow.getFrequency());
		table.setWidget(row.getRowIndex(), NEXT_DATE_COLUMN, (Widget)incomeRow.getNextDate());
		table.setWidget(row.getRowIndex(), AMOUNT_COLUMN, (Widget)incomeRow.getAmount());

	}

	protected Frequency getSelectedFrequency(IncomeRow row)  {
		String frequencyValue = row.getFrequency().getSelected().getValue();
		return Frequency.valueOf(frequencyValue);
	}


}