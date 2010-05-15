package net.switchcase.easymoney.client.view;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import net.switchcase.easymoney.client.common.ModelAdapter;
import net.switchcase.easymoney.shared.Frequency;

public abstract class BaseModelAdapter implements ModelAdapter {

	protected void addHeaderLabel(FlexTable table, String labelText, int column) {
		table.setWidget(0, column, new Label(labelText));
		table.getCellFormatter().addStyleName(0, column, "data-table-header");
	}

	protected void populateFrequency(ListBox frequency)  {
		frequency.addItem(Frequency.BiWeekly.toString(), "Bi-Weekly");
		frequency.addItem(Frequency.Monthly.toString(), "Monthly");
		frequency.addItem(Frequency.SemiMonthly.toString(), "Semi-Monthly");
		frequency.addItem(Frequency.Weekly.toString(), "Weekly");				
	}
	
	protected void setSelectedFrequency(Frequency freq, ListBox freqListBox)  {
		int selectedItem = getSelectedItem(freq, freqListBox);
		if (-1 != selectedItem)  {
			freqListBox.setSelectedIndex(selectedItem);
		}
	}
	
	private int getSelectedItem(Frequency freq, ListBox freqListBox)  {
		if (null != freq)  {
			for(int idx = 0; idx<freqListBox.getItemCount(); idx++)  {
				if (freq.toString().equals(freqListBox.getValue(idx)) ) {
					return idx;
				}
			}
		}
		
		return -1;
		
	}

	
}
