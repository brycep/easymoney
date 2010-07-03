/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.MoneyLabel;
import net.switchcase.easymoney.client.presenter.BudgetPresenter;
import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce Date: May 8, 2010 Time: 3:42:27 PM
 */
public class SummaryView extends Composite implements
		BudgetPresenter.SummaryDisplay {

	private VerticalPanel summaryPanel;
	private Grid layoutGrid;

	private Label owner;
	private TextBox sharedWith;
	private MoneyLabel incomeTotal;
	private MoneyLabel billTotal;
	private MoneyLabel expenseTotal;
	private MoneyLabel balance;

	public SummaryView() {

		summaryPanel = new VerticalPanel();
		initWidget(summaryPanel);

		layoutGrid = new Grid(5, 2);

		Label ownerLabel = new Label("Owner");
		owner = new Label();

		Label sharedWithLabel = new Label("Shared With");
		sharedWith = new TextBox();

		FlowPanel ownerPanel = new FlowPanel();
		ownerPanel.add(ownerLabel);
		ownerPanel.add(owner);
		layoutGrid.setWidget(0, 0, ownerLabel);

		FlowPanel sharedWithPanel = new FlowPanel();
		sharedWithPanel.add(sharedWithLabel);
		sharedWithPanel.add(sharedWith);
		layoutGrid.setWidget(0, 1, sharedWithPanel);

		Label incomeTotalLabel = new Label("Total Income");
		incomeTotal = new MoneyLabel();
		layoutGrid.setWidget(1, 0, incomeTotalLabel);
		layoutGrid.setWidget(1, 1, incomeTotal);

		Label billTotalLabel = new Label("Total Bills");
		billTotal = new MoneyLabel();
		layoutGrid.setWidget(2, 0, billTotalLabel);
		layoutGrid.setWidget(2, 1, billTotal);

		Label expenseTotalLabel = new Label("Total Expenses");
		expenseTotal = new MoneyLabel();
		layoutGrid.setWidget(3, 0, expenseTotalLabel);
		layoutGrid.setWidget(3, 1, expenseTotal);

		Label leftOverLabel = new Label("Balance");
		balance = new MoneyLabel();
		layoutGrid.setWidget(4, 0, leftOverLabel);
		layoutGrid.setWidget(4, 1, balance);

		summaryPanel.add(layoutGrid);
	}

	public void setData(BudgetTo budget) {
		owner.setText(budget.getOwner());
		sharedWith.setValue(budget.getSharedWith());
		incomeTotal.setValue(budget.calculateIncomeTotal());
		billTotal.setValue(budget.calculateBillTotal());
		expenseTotal.setValue(budget.calculateExpenseAmountTotal());
		balance.setValue(budget.calculateBalance());
	}

	public HasMoneyValue getBillTotal() {
		return billTotal;
	}

	public HasMoneyValue getExpenseTotal() {
		return expenseTotal;
	}

	public HasMoneyValue getIncomeTotal() {
		return incomeTotal;
	}

	public HasMoneyValue getBalance() {
		return balance;
	}

	public HasText getOwner() {
		return owner;
	};

	public HasValue<String> getSharedWith() {
		return sharedWith;
	}

}
