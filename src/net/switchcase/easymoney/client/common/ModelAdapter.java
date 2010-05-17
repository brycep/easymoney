package net.switchcase.easymoney.client.common;

import com.google.gwt.user.client.ui.FlexTable;

public interface ModelAdapter {
	
	void renderHeaderRow(FlexTable table);
	ModelObject convertRowToDataObject(int rowIndex, Row row, FlexTable table);
	
	Row createRow(int row);
	void renderRow(Row row, FlexTable table); 

}
