package net.switchcase.easymoney.client.view;

import java.util.ArrayList;
import java.util.List;

import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.ListItem;
import net.switchcase.easymoney.client.common.ModelObject;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.common.ValueListBox;
import net.switchcase.easymoney.shared.BillTo;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BillModelAdapter extends BaseModelAdapter  {
	
	private static final int NAME_COLUMN = 0;
	private static final int REMINDER_COLUMN = 1;
	private static final int DAY_OF_MONTH_COLUMN = 2;
	private static final int REMINDER_DAY_COLUMN = 3;
	private static final int AMOUNT_COLUMN = 4;

	public ModelObject convertRowToDataObject(Row row, FlexTable table) {
		BillRow billRow = (BillRow) row;
		BillTo bill = (BillTo) row.getData();
		bill.setAmount(billRow.getAmount().getMoneyValue());
		bill.setDayOfMonth(getSelectedDay(billRow.getBillDueDay()));
		bill.setReminderDay(getSelectedDay(billRow.getReminderDay()));
		bill.setName(billRow.getName().getValue());
		bill.setReminderActive(billRow.getReminder().getValue());
		return bill;
	}

	public Row createRow(int rowIndex) {
		BillRow row = new BillRow(rowIndex);
		row.setAmount(new MoneyTextBox());
		row.setBillDueDay(createDayList());
		row.setName(new TextBox());
		row.setReminder(new CheckBox());
		row.setReminderDay(createDayList());
		return row;
	}

	public void renderHeaderRow(FlexTable table) {
		addHeaderLabel(table, "Name", NAME_COLUMN);
		addHeaderLabel(table, "Remind Me", REMINDER_COLUMN);
		addHeaderLabel(table, "Bill Due Date", DAY_OF_MONTH_COLUMN);
		addHeaderLabel(table, "Reminder Day", REMINDER_DAY_COLUMN);
		addHeaderLabel(table, "Amount", AMOUNT_COLUMN);
	}

	public void renderRow(Row row, FlexTable table) {
		BillRow billRow = (BillRow) row;
		
		table.setWidget(row.getRowIndex(), NAME_COLUMN, (Widget)billRow.getName());
		table.setWidget(row.getRowIndex(), REMINDER_COLUMN, (Widget)billRow.getReminder());
		table.setWidget(row.getRowIndex(), DAY_OF_MONTH_COLUMN, (Widget)billRow.getBillDueDay());
		table.setWidget(row.getRowIndex(), REMINDER_DAY_COLUMN, (Widget)billRow.getReminderDay());
		table.setWidget(row.getRowIndex(), AMOUNT_COLUMN, (Widget)billRow.getAmount());
	}
	
	private int getSelectedDay(HasValueList valueList)  {
		return Integer.parseInt(valueList.getSelected().getValue());
	}

	private ValueListBox createDayList()  {
		ValueListBox listBox = new ValueListBox();
		List<ListItem> itemList = new ArrayList<ListItem>();
		for(int idx=1; idx<=31; idx++)  {
			String day = Integer.toString(idx);
			itemList.add(new ListItem(day, day));
		}
		listBox.setList(itemList);
		return listBox;
	}
}
