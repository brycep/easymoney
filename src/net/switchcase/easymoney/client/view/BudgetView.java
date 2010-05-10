/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.presenter.BudgetPresenter;
import net.switchcase.easymoney.shared.Budget;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:05:11 PM
 */
public class BudgetView extends Composite implements BudgetPresenter.Display {

    private Label budgetNameLabel;
    private Button saveButton;

    private VerticalPanel budgetPanel;

    private SummaryView summaryView;
    private IncomeView incomeView;
    private BillsView billsView;
    private ExpenseCategoriesView expenseCategoriesView;

    public BudgetView()  {

        budgetPanel = new VerticalPanel();
        initWidget(budgetPanel);
        
        budgetNameLabel = new Label();
        saveButton = new Button("Save");

        HorizontalPanel titlePanel = new HorizontalPanel();
        titlePanel.add(budgetNameLabel);
        titlePanel.add(saveButton);
        titlePanel.addStyleName("bottom-spacing");
        budgetPanel.add(titlePanel);

        TabPanel tabPanel = new TabPanel();

        budgetNameLabel = new Label();
        saveButton = new Button();

        summaryView = new SummaryView();
        incomeView = new IncomeView();
        billsView = new BillsView();
        expenseCategoriesView = new ExpenseCategoriesView();

        tabPanel.add(summaryView, "Summary");
        tabPanel.add(incomeView, "Income");
        tabPanel.add(billsView, "Bills");
        tabPanel.add(expenseCategoriesView, "Expenses");
        tabPanel.selectTab(0);
        budgetPanel.add(tabPanel);
    }

    public void setData(Budget budget) {
        budgetNameLabel.setText(budget.getName());
        summaryView.setData(budget);
        incomeView.setData(budget);
        billsView.setData(budget);
        expenseCategoriesView.setData(budget);
    }

    public String getBudgetName() {
        return budgetNameLabel.getText();
    }

    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    public Widget getSummaryView() {
        return summaryView;
    }

    public Widget getIncomeView() {
        return incomeView;
    }

    public Widget getBillsView() {
        return billsView;
    }

    public Widget getExpenseCategoriesView() {
        return expenseCategoriesView;
    }

    public Widget asWidget() {
        return this;
    }    
    
}
