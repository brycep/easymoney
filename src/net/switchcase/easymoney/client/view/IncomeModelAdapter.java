package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.ModelAdapter;
import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.Income;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class IncomeModelAdapter implements ModelAdapter {

	public ModelObject convertRowToDataObject(int row, ModelObject dataObject, FlexTable table) {
		
		return null;
	}

	public void renderRow(int row, ModelObject dataObject, FlexTable table) {
		
		TextBox name = new TextBox();
		TextBox amount = new TextBox();
		ListBox frequency = new ListBox();
		DateBox nextDate = new DateBox();
		
		populateFrequency(frequency);
		
		if (dataObject instanceof Income)  {
			Income income = (Income) dataObject;
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
		
		table.setWidget(row, 0, name);
		table.setWidget(row, 1, amount);
		table.setWidget(row, 2, frequency);
		table.setWidget(row, 3, nextDate);

	}
	
	private void populateFrequency(ListBox frequency)  {
		frequency.addItem("Bi-Weekly");
		frequency.addItem("Semi-Monthly");
	}
	
	// I know there are better ways to do this but I'm lazy right now...
	private void setSelectedFrequency(Frequency freq, ListBox freqListBox)  {
		if (null != freq)  {
			if (Frequency.BiWeekly.equals(freq))  {
				freqListBox.setSelectedIndex(0);
			} else if (Frequency.SemiMonthly.equals(freq)) {
				freqListBox.setSelectedIndex(1);
			}
		}
	}

}