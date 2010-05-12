package net.switchcase.easymoney.client;

import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
@RemoteServiceRelativePath("budget")
public interface EasyMoneyService extends RemoteService {

	BudgetTo getActiveBudget();
	
}
