/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.client.presenter.BudgetPresenter;
import net.switchcase.easymoney.client.presenter.BudgetPresenter.BillsDisplay;
import net.switchcase.easymoney.client.presenter.BudgetPresenter.ExpenseDisplay;
import net.switchcase.easymoney.client.presenter.BudgetPresenter.IncomeDisplay;
import net.switchcase.easymoney.client.presenter.BudgetPresenter.SummaryDisplay;
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.IncomeTo;

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
        budgetPanel.setWidth("100%");
        budgetPanel.add(titlePanel);

        TabPanel tabPanel = new TabPanel();

        budgetNameLabel = new Label();
        saveButton = new Button();

        summaryView = new SummaryView();
        incomeView = new IncomeView();
        billsView = new BillsView();
        expenseCategoriesView = new ExpenseCategoriesView();

        tabPanel.setWidth("100%");
        tabPanel.addStyleName("budget-tab-panel");
        tabPanel.add(summaryView, "Summary");
        tabPanel.add(incomeView, "Income");
        tabPanel.add(billsView, "Bills");
        tabPanel.add(expenseCategoriesView, "Expenses");
        tabPanel.selectTab(0);
        budgetPanel.add(tabPanel);
    }

    public void setData(BudgetTo budget) {
        budgetNameLabel.setText(budget.getName());
        summaryView.setData(budget);
        incomeView.setData(budget);
        billsView.setData(budget);
        expenseCategoriesView.setData(budget);
    }
    
    public void addIncome(IncomeTo income) {
    	incomeView.addIncome(income);
    }
    
    public void addBill(BillTo bill) {
    	billsView.addBill(bill);
    }
    
    public void addExpenseCategory(ExpenseCategoryTo expenseCategory) {
    	expenseCategoriesView.addExpenseCategory(expenseCategory);
    }
    
    public String getBudgetName() {
        return budgetNameLabel.getText();
    }

    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    public SummaryDisplay getSummaryView() {
        return summaryView;
    }

    public IncomeDisplay getIncomeView() {
        return incomeView;
    }

    public BillsDisplay getBillsView() {
        return billsView;
    }

    public ExpenseDisplay getExpenseCategoriesView() {
        return expenseCategoriesView;
    }

    public Widget asWidget() {
        return this;
    }    
    
    public void disableSaveButton() {
    	saveButton.setEnabled(false);
    }
    
    public void enableSaveButton() {
    	saveButton.setEnabled(true);
    }
    
}
