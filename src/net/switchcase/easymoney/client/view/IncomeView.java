/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.DataTable;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:39 PM
 */
public class IncomeView extends Composite  {
	
	private VerticalPanel incomeViewPanel;
	private DataTable<IncomeTo> incomeListTable;
	private Label totalLabel;
	
	public IncomeView()  {
		incomeViewPanel = new VerticalPanel();
		initWidget(incomeViewPanel);
		
		incomeListTable = new DataTable<IncomeTo>(new IncomeModelAdapter());
		incomeListTable.addStyleName("money-table");
		incomeListTable.setCellPadding(0);
		incomeListTable.setCellSpacing(0);
		incomeListTable.setTotalLabel(totalLabel);

		incomeViewPanel.add(incomeListTable);
	}

	public HasText totalIncomeLabel()  {
		return totalLabel;
	}
	
	public void setData(BudgetTo budget)  {
		incomeListTable.setData(budget.getIncomes());
	}
}
