package net.switchcase.easymoney.client.common;

import net.switchcase.easymoney.client.event.RowValueChangeHandler;

import com.google.gwt.user.client.ui.FlexTable;

public interface ModelAdapter {
	
	void renderHeaderRow(FlexTable table);
	ModelObject convertRowToDataObject(Row row, FlexTable table);
	
	Row createRow(int row, final RowValueChangeHandler dataTable);
	void renderRow(Row row, FlexTable table); 

}
