package net.switchcase.easymoney.client;

import net.switchcase.easymoney.shared.LoginInfo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:36:18 PM
 */
public class EasyMoney implements EntryPoint {
    public void onModuleLoad() {

        final EasyMoneyServiceAsync easyMoneyService = GWT.create(EasyMoneyService.class);
        
        easyMoneyService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>()  {
        		public void onSuccess(LoginInfo result) {
        			if (result.isLoggedIn())  {
	        	        HandlerManager eventBus = new HandlerManager(null);
	        	        AppController appController = new AppController(easyMoneyService, eventBus, result);
	        	        appController.go(RootPanel.get("easyMoneyApp"));
        			} else  {
        				Window.open(result.getLoginUrl(), "_self", "");
        			}
        		}
        		
        		public void onFailure(Throwable caught) {
        			
        		}
        });

    }
}
