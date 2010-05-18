/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.DataTable;
import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.MoneyLabel;
import net.switchcase.easymoney.client.event.HasRowValueChangeHandler;
import net.switchcase.easymoney.client.presenter.BudgetPresenter;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:43:05 PM
 */
public class ExpenseCategoriesView extends Composite implements BudgetPresenter.ExpenseDisplay  {
	
	private VerticalPanel expenseViewPanel;
	private DataTable<ExpenseCategoryTo> expenseListTable;
	private MoneyLabel totalLabel = new MoneyLabel();

	public ExpenseCategoriesView()  {
		expenseViewPanel = new VerticalPanel();
		initWidget(expenseViewPanel);
		
		expenseListTable = new DataTable<ExpenseCategoryTo>(new ExpenseModelAdapter());
		expenseListTable.addStyleName("money-table");
		expenseListTable.setCellPadding(0);
		expenseListTable.setCellSpacing(0);
		expenseListTable.setTotalLabel(totalLabel);

		expenseViewPanel.add(expenseListTable);
	}
	
	public HasRowValueChangeHandler getExpenseTable() {
		return expenseListTable;
	}

	public HasMoneyValue getTotalExpenseLabel()  {
		return totalLabel;
	}
	
	public void setData(BudgetTo budget)  {
		expenseListTable.setData(budget.getCategories());
	}
}
