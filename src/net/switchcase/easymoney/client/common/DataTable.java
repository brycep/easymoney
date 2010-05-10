package net.switchcase.easymoney.client.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DataTable extends FlexTable {
	
	private List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
	private List<DataRow> rows = new ArrayList<DataRow>();
	private ModelAdapter modelAdapter;

	public DataTable(List<ColumnDefinition> columnDefinitions,
			ModelAdapter modelAdapter) {
		super();
		this.columnDefinitions = columnDefinitions;
		this.modelAdapter = modelAdapter;
	}
	
	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}
	
	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}
	
	public List<DataRow> getRows() {
		return rows;
	}
	
	public void setRows(List<DataRow> rows) {
		this.rows = rows;
	}
	
	public void setData(List<ModelObject> dataList)  {
		createHeaderRow();

		int rowIndex = 1;
		for(ModelObject object : dataList)  {
			modelAdapter.renderRow(rowIndex, object, this);
			rowIndex++;
		}
	}
	
	private void createHeaderRow()  {
		int columnIndex = 0;
		for(ColumnDefinition columnDef : columnDefinitions)  {
			this.setWidget(0, columnIndex, new Label(columnDef.getHeaderLabel()));
			columnIndex++;
		}
	}

}
