package net.switchcase.easymoney.client.common;

import com.google.gwt.user.client.ui.FlexTable;

public interface ModelAdapter {
	
	void renderHeaderRow(FlexTable table);
	ModelObject convertRowToDataObject(int row, ModelObject dataObject, FlexTable table);
	void renderRow(int row, ModelObject dataObject, FlexTable table); 

}
