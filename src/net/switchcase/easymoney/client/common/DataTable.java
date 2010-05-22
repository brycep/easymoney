package net.switchcase.easymoney.client.common;

import java.util.ArrayList;
import java.util.List;

import net.switchcase.easymoney.client.event.HasRowValueChangeHandler;
import net.switchcase.easymoney.client.event.RowValueChangeHandler;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DataTable<ModelType> extends FlexTable implements HasRowValueChangeHandler, RowValueChangeHandler {
	
	private List<Row> rows = new ArrayList<Row>();
	private ModelAdapter modelAdapter;
	private List<RowValueChangeHandler> changeHandlers = new ArrayList<RowValueChangeHandler>();
	
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
			Row row = modelAdapter.createRow(rowIndex, this);
			row.setData(object);
			modelAdapter.renderRow(row, this);
			rowIndex++;
		}
		
		if (null != totalLabel)  {
			createFooterRow(rowIndex);
		}
	}
	
	public void addRow(ModelType model)  {
		int lastRow = this.getRowCount() - 1;
		Row row = modelAdapter.createRow(lastRow, this);
		row.setData(model);
		modelAdapter.renderRow(row, this);
		createFooterRow(lastRow + 1);
	}
	
	private void createFooterRow(int row)  {
		this.setWidget(row, this.getCellCount(row - 1) - 2, new Label("Total: "));
		this.getCellFormatter().addStyleName(row, this.getCellCount(row - 1) - 2, "right-align");
		this.setWidget(row, this.getCellCount(row - 1) - 1, totalLabel);
		this.getCellFormatter().addStyleName(row, this.getCellCount(row - 1) - 1, "right-align");
		this.getRowFormatter().addStyleName(row, "data-table-footer");
	}
	
	public void addRowValueChangeHandler(RowValueChangeHandler handler) {
		changeHandlers.add(handler);
		
	}

	public void onRowValueChanged(Row row) {
		for(RowValueChangeHandler handler : changeHandlers)  {
			handler.onRowValueChanged(row);
		}
	}

}
