package net.switchcase.easymoney.client.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DataTable<ModelType> extends FlexTable {
	
	private List<Row> rows = new ArrayList<Row>();
	private ModelAdapter modelAdapter;
	
	private Label totalLabel;

	public DataTable(ModelAdapter modelAdapter) {
		super();
		this.modelAdapter = modelAdapter;
	}
	
	public List<Row> getRows() {
		return rows;
	}
	
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	public Label getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(Label totalLabel) {
		this.totalLabel = totalLabel;
	}

	public void setData(List<ModelType> dataList)  {
		modelAdapter.renderHeaderRow(this);

		int rowIndex = 1;
		for(ModelType object : dataList)  {
			Row row = modelAdapter.createRow(rowIndex);
			row.setData(object);
			modelAdapter.renderRow(row, this);
			rowIndex++;
		}
		
		if (null != totalLabel)  {
			createFooterRow(rowIndex);
		}
	}
	
	private void createFooterRow(int row)  {
		this.setWidget(row, this.getCellCount(row - 2), new Label("Total: "));
		this.setWidget(row, this.getCellCount(row - 1), totalLabel);
		this.getRowFormatter().addStyleName(row, "data-table-footer");
	}

}
