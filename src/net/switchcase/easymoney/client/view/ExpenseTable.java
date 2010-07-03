package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.DataTable;
import net.switchcase.easymoney.client.common.ModelAdapter;
import net.switchcase.easymoney.shared.CashEnvelopeTo;

import com.google.gwt.user.client.ui.Label;

public class ExpenseTable extends DataTable<CashEnvelopeTo> {

	private Label amountLabel;
	
	public Label getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(Label amountLabel) {
		this.amountLabel = amountLabel;
	}

	public ExpenseTable(ModelAdapter modelAdapter) {
		super(modelAdapter);
	}
	
	protected void createFooterRow(int row)  {
		this.setWidget(row, this.getCellCount(row - 1) - 3, new Label("Total: "));
		this.getCellFormatter().addStyleName(row, this.getCellCount(row - 1) - 3, "right-align");
		this.setWidget(row, this.getCellCount(row - 1) - 2, getAmountLabel());
		this.getCellFormatter().addStyleName(row, this.getCellCount(row - 1) - 2, "right-align");
		this.setWidget(row, this.getCellCount(row - 1) - 1, getTotalLabel());
		this.getCellFormatter().addStyleName(row, this.getCellCount(row - 1) - 1, "right-align");
		this.getRowFormatter().addStyleName(row, "data-table-footer");
	}
	

}
