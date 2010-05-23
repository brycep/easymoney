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
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:39 PM
 */
public class IncomeView extends Composite implements BudgetPresenter.IncomeDisplay {
	
	private VerticalPanel incomeViewPanel;
	private DataTable<IncomeTo> incomeListTable;
	private MoneyLabel totalLabel;
	private Button addIncomeButton;
	private FlowPanel buttonPanel;
	
	public IncomeView()  {
		incomeViewPanel = new VerticalPanel();
		initWidget(incomeViewPanel);
		
		addIncomeButton = new Button();
		addIncomeButton.setText("Add");

		buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("button-panel");
		buttonPanel.add(addIncomeButton);
		incomeViewPanel.add(buttonPanel);
		
		totalLabel = new MoneyLabel();
		
		incomeListTable = new DataTable<IncomeTo>(new IncomeModelAdapter());
		incomeListTable.addStyleName("money-table");
		incomeListTable.setCellPadding(0);
		incomeListTable.setCellSpacing(0);
		incomeListTable.setTotalLabel(totalLabel);

		incomeViewPanel.add(incomeListTable);
	}

	public HasClickHandlers getAddIncomeButton() {
		return addIncomeButton;
	}
	
	public HasRowValueChangeHandler getIncomeTable() {
		return incomeListTable;
	}

	public HasMoneyValue getTotalLabel() {
		return totalLabel;
	}
	
	public void setData(BudgetTo budget)  {
		incomeListTable.setData(budget.getIncomes());
		totalLabel.setValue(budget.calculateIncomeTotal());
	}
	
    public void addIncome(IncomeTo income) {
    	incomeListTable.addRow(income);
    }
    
    public void updateModel() {
    	incomeListTable.updateModel();
    }
}
