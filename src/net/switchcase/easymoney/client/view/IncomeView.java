/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import java.util.Arrays;

import net.switchcase.easymoney.client.common.ColumnDefinition;
import net.switchcase.easymoney.client.common.DataTable;
import net.switchcase.easymoney.shared.Budget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:39 PM
 */
public class IncomeView extends Composite  {
	
	private VerticalPanel incomeViewPanel;
	private DataTable incomeListTable;
	
	public IncomeView()  {
		incomeViewPanel = new VerticalPanel();
		initWidget(incomeViewPanel);
		
		incomeListTable = new DataTable(Arrays.asList(
				new ColumnDefinition("Name", ""),
				new ColumnDefinition("Amount", ""),
				new ColumnDefinition("Frequency", ""),
				new ColumnDefinition("Next Pay Date", "")
		), new IncomeModelAdapter());

		incomeViewPanel.add(incomeListTable);
	}
	
	public void setData(Budget budget)  {
		incomeListTable.setData(budget.getIncomes());
	}
}
