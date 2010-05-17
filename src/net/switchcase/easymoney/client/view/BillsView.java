/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.DataTable;
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:51 PM
 */
public class BillsView extends Composite {
	
	private VerticalPanel billsPanel;
	private DataTable<BillTo> billListTable;
	private Label totalLabel;

	public BillsView()  {
		billsPanel = new VerticalPanel();
		initWidget(billsPanel);

		billListTable = new DataTable<BillTo>(new BillModelAdapter());
		billListTable.addStyleName("money-table");
		billListTable.setCellPadding(0);
		billListTable.setCellSpacing(0);
		billListTable.setTotalLabel(totalLabel);

		billsPanel.add(billListTable);

	}
	
	public void setData(BudgetTo budget)  {
		billListTable.setData(budget.getMonthlyBills());
	}

}
