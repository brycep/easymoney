package net.switchcase.easymoney.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:36:18 PM
 */
public class EasyMoney implements EntryPoint {
    public void onModuleLoad() {

        EasyMoneyServiceAsync easyMoneyService = GWT.create(EasyMoneyService.class);
        HandlerManager eventBus = new HandlerManager(null);

        AppController appController = new AppController(easyMoneyService, eventBus);

        appController.go(RootPanel.get("easyMoneyApp"));
    }
}
