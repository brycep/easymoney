/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.presenter;

import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.shared.Budget;

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
        // get budget;
    }
}
