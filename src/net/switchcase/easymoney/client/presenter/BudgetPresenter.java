/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.presenter;

import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.event.HasRowValueChangeHandler;
import net.switchcase.easymoney.client.event.RowValueChangeHandler;
import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:25:53 PM
 */
public class BudgetPresenter implements Presenter {

    private final EasyMoneyServiceAsync easyMoneyService;
//    private final HandlerManager eventBus;
    private final Display display;

    private BudgetTo budget;
    
    public interface Display {

        String getBudgetName(); 
        HasClickHandlers getSaveButton();

        Widget getSummaryView();
        Widget getIncomeView();
        Widget getBillsView();
        ExpenseDisplay getExpenseCategoriesView();

        void setData(BudgetTo budget);

        Widget asWidget();
    }
    
    public interface ExpenseDisplay  {
    	HasRowValueChangeHandler getExpenseTable(); 
    	HasMoneyValue getTotalExpenseLabel();
    }

    public BudgetPresenter(EasyMoneyServiceAsync easyMoneyService,
                           HandlerManager eventBus,
                           Display display) {
        this.easyMoneyService = easyMoneyService;
//        this.eventBus = eventBus;
        this.display = display;
    }
    
    public Display getDisplay()  {
    	return display;
    }

    public void bind()  {
    	display.getExpenseCategoriesView().getExpenseTable().addRowValueChangeHandler(
    			new RowValueChangeHandler()  {
					public void onRowValueChanged(Row row) {
						row.updateModel();
						expenseAmountsChanged();
					}
    			}
    	);
    
    }

    public void go(HasWidgets container) {
    	bind();
        container.clear();
        container.add(display.asWidget());
        retrieveBudget();
    }
    
    public void expenseAmountsChanged()  {
    	display.getExpenseCategoriesView().getTotalExpenseLabel().setValue(budget.calulateExpenseTotal());
    }
    
    public BudgetTo getBudget()  {
    	return budget;
    }
    
    public void setBudget(BudgetTo budgetTo)  {
    	this.budget = budgetTo;
    }
    
    private void retrieveBudget()  {
    	easyMoneyService.getActiveBudget(new AsyncCallback<BudgetTo>()  {

			public void onFailure(Throwable caught) {
				Window.alert("Could not retrieve your active budget.");
			}

			public void onSuccess(BudgetTo result) {
				BudgetPresenter.this.setBudget(result);
				display.setData(result);
			}
    		
    	});
    }
}
