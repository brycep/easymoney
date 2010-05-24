package net.switchcase.easymoney.client.view;

import java.util.ArrayList;
import java.util.List;

import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.ListItem;
import net.switchcase.easymoney.client.common.MoneyTextBox;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.common.ValueListBox;
import net.switchcase.easymoney.client.event.RowValueChangeHandler;
import net.switchcase.easymoney.shared.BillTo;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class BillModelAdapter extends BaseModelAdapter  {
	
	private static final int NAME_COLUMN = 0;
	private static final int DAY_OF_MONTH_COLUMN = 1;
	private static final int NEXT_DUE_DATE = 2;
	private static final int AMOUNT_COLUMN = 3;

	public Object convertRowToDataObject(Row row, FlexTable table) {
		BillRow billRow = (BillRow) row;
		BillTo bill = (BillTo) row.getData();
		bill.setAmount(billRow.getAmount().getMoneyValue());
		bill.setDayOfMonth(getSelectedDay(billRow.getBillDueDay()));
		bill.setNextDueDate(billRow.getNextDueDate().getValue());
		bill.setName(billRow.getName().getValue());
		return bill;
	}

	public Row createRow(int rowIndex, final RowValueChangeHandler dataTable) {
		final BillRow row = new BillRow(rowIndex);
		row.setBillDueDay(createDayList());
		row.setNextDueDate(new DateBox());
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

	public void renderHeaderRow(FlexTable table) {
		addHeaderLabel(table, "Name", NAME_COLUMN);
		addHeaderLabel(table, "Bill Due \n Day of Month", DAY_OF_MONTH_COLUMN);
		addHeaderLabel(table, "Next Due Date", NEXT_DUE_DATE);
		addHeaderLabel(table, "Amount", AMOUNT_COLUMN);
	}

	public void renderRow(Row row, FlexTable table) {
		BillRow billRow = (BillRow) row;
		
		table.setWidget(row.getRowIndex(), NAME_COLUMN, (Widget)billRow.getName());
		table.setWidget(row.getRowIndex(), DAY_OF_MONTH_COLUMN, (Widget)billRow.getBillDueDay());
		table.setWidget(row.getRowIndex(), AMOUNT_COLUMN, (Widget)billRow.getAmount());
		table.setWidget(row.getRowIndex(), NEXT_DUE_DATE, (Widget)billRow.getNextDueDate());
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
