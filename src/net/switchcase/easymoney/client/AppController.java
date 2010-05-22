/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client;

import net.switchcase.easymoney.client.presenter.BudgetPresenter;
import net.switchcase.easymoney.client.presenter.Presenter;
import net.switchcase.easymoney.client.view.BudgetView;
import net.switchcase.easymoney.shared.LoginInfo;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * User: bryce
 * Date: May 4, 2010
 * Time: 6:37:11 PM
 */
public class AppController implements ValueChangeHandler<String> {

    private EasyMoneyServiceAsync easyMoneyService;
    private HandlerManager eventBus;
    private HasWidgets container;
    private LoginInfo loginInfo;

    public AppController(EasyMoneyServiceAsync easyMoneyService,
                         HandlerManager eventBus,
                         LoginInfo loginInfo) {
        this.easyMoneyService = easyMoneyService;
        this.eventBus = eventBus;
        this.loginInfo = loginInfo;
        bind();
    }

    private void bind()  {
        History.addValueChangeHandler(this);
        
    }

    public void go(final HasWidgets container)  {
        this.container = container;
        if ("".equals(History.getToken()))  {
            History.newItem("budget");
        } else  {
            History.fireCurrentHistoryState();
        }
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null)  {
            Presenter presenter = new BudgetPresenter(easyMoneyService, eventBus, new BudgetView());
            presenter.go(container);

        }
    }
}
