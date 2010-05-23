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
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:51 PM
 */
public class BillsView extends Composite implements BudgetPresenter.BillsDisplay {
	
	private VerticalPanel billsPanel;
	private FlowPanel buttonPanel; 
	private DataTable<BillTo> billListTable;
	private MoneyLabel totalLabel;
	private Button addBillButton;

	public BillsView()  {
		billsPanel = new VerticalPanel();
		initWidget(billsPanel);

		addBillButton = new Button();
		addBillButton.setText("Add");

		buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("button-panel");
		buttonPanel.add(addBillButton);
		billsPanel.add(buttonPanel);
		
		totalLabel = new MoneyLabel();
		
		billListTable = new DataTable<BillTo>(new BillModelAdapter());
		billListTable.addStyleName("money-table");
		billListTable.setCellPadding(0);
		billListTable.setCellSpacing(0);
		billListTable.setTotalLabel(totalLabel);

		billsPanel.add(billListTable);

	}
	
	public HasClickHandlers getAddBillButton() {
		return addBillButton;
	}
	
	public HasRowValueChangeHandler getBillsTable() {
		return billListTable;
	}
	
	public HasMoneyValue getTotalLabel() {
		return totalLabel;
	}
	
	public void setData(BudgetTo budget)  {
		billListTable.setData(budget.getMonthlyBills());
		totalLabel.setValue(budget.calculateBillTotal());
	}
	
	public void addBill(BillTo bill)  {
		billListTable.addRow(bill);
	}
	
	public void updateModel() {
		billListTable.updateModel();
	}

}
