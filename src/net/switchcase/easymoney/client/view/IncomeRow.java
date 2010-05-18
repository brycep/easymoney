package net.switchcase.easymoney.client.view;

import java.util.Arrays;
import java.util.Date;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.ListItem;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class IncomeRow implements Row {
	
	private int rowIndex;
	private IncomeTo dataObject;
	
	private HasValue<String> name;
	private HasMoneyValue amount;
	private HasValueList frequency;
	private HasValue<Date> nextDate;

	public IncomeRow(int rowIndex)  {
		this.rowIndex = rowIndex;
	}
	
	public void setData(Object dataObject)  {
		IncomeTo incomeTo = (IncomeTo) dataObject;
		this.dataObject = incomeTo;
		name.setValue(incomeTo.getName());
		amount.setValue(incomeTo.getAmount());
		nextDate.setValue(incomeTo.getNextPayDate());
		frequency.setList(Arrays.asList(
				new ListItem(Frequency.BiWeekly.toString(), "Bi-Weekly"),
				new ListItem(Frequency.Monthly.toString(), "Monthly"),
				new ListItem(Frequency.SemiMonthly.toString(), "Semi-Monthly"),
				new ListItem(Frequency.Weekly.toString(), "Weekly")
		));
		if (null != incomeTo.getFrequency())  {
			frequency.setSelected(incomeTo.getFrequency().toString());
		}
	}
	
	public void updateModel() {
		dataObject.setName(name.getValue());
		dataObject.setAmount(amount.getMoneyValue());
		dataObject.setNextPayDate(nextDate.getValue());
		if (null != frequency.getSelected())  {
			dataObject.setFrequency(Frequency.valueOf(frequency.getSelected().getValue()) );
		}
	}

	public int getRowIndex()  {
		return rowIndex;
	}
	
	public void setRowIndex(int rowIndex)  {
		this.rowIndex = rowIndex;
	}
	
	public IncomeTo getData() {
		return dataObject;
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

	public HasValueList getFrequency() {
		return frequency;
	}

	public void setFrequency(HasValueList frequency) {
		this.frequency = frequency;
	}

	public HasValue<Date> getNextDate() {
		return nextDate;
	}

	public void setNextDate(HasValue<Date> nextDate) {
		this.nextDate = nextDate;
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
