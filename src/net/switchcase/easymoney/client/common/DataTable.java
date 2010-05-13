package net.switchcase.easymoney.client.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DataTable extends FlexTable {
	
	private List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
	private List<DataRow> rows = new ArrayList<DataRow>();
	private ModelAdapter modelAdapter;
	
	private Label totalLabel;

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
	
	public Label getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(Label totalLabel) {
		this.totalLabel = totalLabel;
	}

	@SuppressWarnings("unchecked")
	public void setData(List dataList)  {
		createHeaderRow();

		int rowIndex = 1;
		for(Object object : dataList)  {
			if (object instanceof ModelObject)  {
				modelAdapter.renderRow(rowIndex, (ModelObject)object, this);
				rowIndex++;
			}
		}
		
		if (null != totalLabel)  {
			createFooterRow(rowIndex);
		}
	}
	
	private void createHeaderRow()  {
		int columnIndex = 0;
		for(ColumnDefinition columnDef : columnDefinitions)  {
			this.setWidget(0, columnIndex, new Label(columnDef.getHeaderLabel()));
			this.getCellFormatter().addStyleName(0, columnIndex, "data-table-header");
			columnIndex++;
		}
	}
	
	private void createFooterRow(int row)  {
		this.setWidget(row, this.getCellCount(row - 2), new Label("Total: "));
		this.setWidget(row, this.getCellCount(row - 1), totalLabel);
		this.getRowFormatter().addStyleName(row, "data-table-footer");
	}

}
