/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.shared.Bill;
import net.switchcase.easymoney.shared.Budget;
import net.switchcase.easymoney.shared.ExpenseCategory;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.Income;
import net.switchcase.easymoney.shared.Money;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:25:53 PM
 */
public class BudgetPresenter implements Presenter {

    private final EasyMoneyServiceAsync easyMoneyService;
    private final HandlerManager eventBus;
    private final Display display;

    public interface Display {

        String getBudgetName(); 
        HasClickHandlers getSaveButton();

        Widget getSummaryView();
        Widget getIncomeView();
        Widget getBillsView();
        Widget getExpenseCategoriesView();

        void setData(Budget budget);

        Widget asWidget();
    }

    public BudgetPresenter(EasyMoneyServiceAsync easyMoneyService,
                           HandlerManager eventBus,
                           Display display) {
        this.easyMoneyService = easyMoneyService;
        this.eventBus = eventBus;
        this.display = display;
    }

    public void bind()  {
        
    }

    public void go(HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
        retrieveBudget();
    }
    
    private void retrieveBudget()  {
    		Budget testBudget = new Budget();
    		testBudget.setName("Test Budget");
    		
    		testBudget.setBalance(new Money(400, 00));
    		testBudget.setMonthlySavings(new Money(100, 00));
    		
    		List<Income> incomes = new ArrayList<Income>();
    		Income income1 = new Income();
    		income1.setAmount(new Money(2000, 00));
    		income1.setFrequency(Frequency.BiWeekly);
    		income1.setName("Salary");
    		income1.setNextPayDate(new Date());
    		
    		incomes.add(income1);
    		testBudget.setIncomes(incomes);

    		List<Bill> monthlyBills = new ArrayList<Bill>();
    		Bill bill1 = new Bill();
    		bill1.setReminderActive(false);
    		bill1.setDayOfMonth(10);
    		bill1.setReminderDay(5);
    		monthlyBills.add(bill1);
    		testBudget.setMonthlyBills(monthlyBills);
    		
    		List<ExpenseCategory> expenseCategories = new ArrayList<ExpenseCategory>();
    		ExpenseCategory expenseCategory1 = new ExpenseCategory();
    		expenseCategory1.setAccumulating(false);
    		expenseCategory1.setAmount(new Money(300, 00));
    		expenseCategory1.setName("Groceries");
    		expenseCategory1.setBalance(new Money(140, 30));
    		expenseCategory1.setFrequencyToRefresh(Frequency.Monthly);
    		expenseCategories.add(expenseCategory1);
    		
    		ExpenseCategory expenseCategory2 = new ExpenseCategory();
    		expenseCategory2.setAccumulating(false);
    		expenseCategory2.setAmount(new Money(250, 00));
    		expenseCategory2.setName("Fuel");
    		expenseCategory2.setBalance(new Money(102, 65));
    		expenseCategory2.setFrequencyToRefresh(Frequency.Monthly);
    		expenseCategories.add(expenseCategory2);
    		
    		testBudget.setCategories(expenseCategories);
    		
    		display.setData(testBudget);
    }
}
