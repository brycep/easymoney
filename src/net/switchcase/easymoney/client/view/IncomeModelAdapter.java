package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelAdapter;
import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
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
	
	public ModelObject convertRowToDataObject(int row, ModelObject dataObject, FlexTable table) {
		return null;
	}

	public void renderRow(int row, ModelObject dataObject, FlexTable table) {
		
		TextBox name = new TextBox();
		TextBox amount = new TextBox();
		ListBox frequency = new ListBox();
		DateBox nextDate = new DateBox();
		nextDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		
		populateFrequency(frequency);
		
		if (dataObject instanceof IncomeTo)  {
			IncomeTo income = (IncomeTo) dataObject;
			if (null != income.getName())  {
				name.setValue(income.getName());
			}
			if (null != income.getAmount())  {
				amount.setValue(income.getAmount().toString());
			}
			if (null != income.getNextPayDate())  {
				nextDate.setValue(income.getNextPayDate());
			}
			
			setSelectedFrequency(income.getFrequency(), frequency);
		}
		
		table.setWidget(row, NAME_COLUMN, name);
		table.setWidget(row, FREQUENCY_COLUMN, frequency);
		table.setWidget(row, NEXT_DATE_COLUMN, nextDate);
		table.setWidget(row, AMOUNT_COLUMN, amount);

	}
	

}