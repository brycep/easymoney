package net.switchcase.easymoney.client;

import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.LoginInfo;
import net.switchcase.easymoney.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
@RemoteServiceRelativePath("budget")
public interface EasyMoneyService extends RemoteService {
	
	LoginInfo login(String requestUri);
	BudgetTo getActiveBudget() throws NotLoggedInException;
	void saveBudget(BudgetTo budget) throws NotLoggedInException;
	
}
