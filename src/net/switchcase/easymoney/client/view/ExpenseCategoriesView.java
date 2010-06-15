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
import net.switchcase.easymoney.shared.CashEnvelopeTo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:43:05 PM
 */
public class ExpenseCategoriesView extends Composite implements BudgetPresenter.ExpenseDisplay  {
	
	private VerticalPanel expenseViewPanel;
	private FlowPanel buttonPanel;
	private DataTable<CashEnvelopeTo> expenseListTable;
	private MoneyLabel totalLabel = new MoneyLabel();
	private Button addExpenseCategoryButton;

	public ExpenseCategoriesView()  {
		expenseViewPanel = new VerticalPanel();
		initWidget(expenseViewPanel);
		
		addExpenseCategoryButton = new Button();
		addExpenseCategoryButton.setText("Add");
		
		buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("button-panel");
		buttonPanel.add(addExpenseCategoryButton);
		expenseViewPanel.add(buttonPanel);
		
		expenseListTable = new DataTable<CashEnvelopeTo>(new ExpenseModelAdapter());
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
	
	public HasClickHandlers getAddExpenseCategoryButton()  {
		return addExpenseCategoryButton;
	}
	
	public void setData(BudgetTo budget)  {
		totalLabel.setValue(budget.calculateExpenseTotal());
		expenseListTable.setData(budget.getExpenses());
	}
	
	public void addExpenseCategory(CashEnvelopeTo expenseCategory)  {
		expenseListTable.addRow(expenseCategory);
	}
	
	public void updateModel() {
		expenseListTable.updateModel();
	}
}
