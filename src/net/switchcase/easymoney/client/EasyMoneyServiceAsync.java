package net.switchcase.easymoney.client;

import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.LoginInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Easy Money Main Service Class.   This class handles logins and initial
 * budget retrieval.   If we get too crazy with methods here, then we can go
 * ahead and split this out.
 *
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
public interface EasyMoneyServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);
	void getActiveBudget(AsyncCallback<BudgetTo> callback);
	void saveBudget(BudgetTo budget, AsyncCallback<BudgetTo> callback);
	
}
