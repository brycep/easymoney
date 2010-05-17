package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.common.ValueListBox;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class IncomeModelAdapter extends BaseModelAdapter {

	private static final int NAME_COLUMN = 0;
	private static final int FREQUENCY_COLUMN = 1;
	private static final int NEXT_DATE_COLUMN = 2;
	private static final int AMOUNT_COLUMN = 3;
	
	public void renderHeaderRow(FlexTable table)  {
		table.setWidget(0, NAME_COLUMN, new Label("Name"));
		table.getCellFormatter().addStyleName(0, NAME_COLUMN, "data-table-header");
		
		table.setWidget(0, FREQUENCY_COLUMN, new Label("Frequency"));
		table.getCellFormatter().addStyleName(0, FREQUENCY_COLUMN, "data-table-header");
		
		table.setWidget(0, NEXT_DATE_COLUMN, new Label("Next Date"));
		table.getCellFormatter().addStyleName(0, NEXT_DATE_COLUMN, "data-table-header");
		
		table.setWidget(0, AMOUNT_COLUMN, new Label("Amount"));
		table.getCellFormatter().addStyleName(0, AMOUNT_COLUMN, "data-table-header");

	}
	
	
	public ModelObject convertRowToDataObject(int row, Row incomeRow, FlexTable table) {
		return null;
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


}