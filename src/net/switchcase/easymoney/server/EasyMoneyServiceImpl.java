package net.switchcase.easymoney.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.IncomeTo;
import net.switchcase.easymoney.shared.LoginInfo;
import net.switchcase.easymoney.shared.MoneyTo;
import net.switchcase.easymoney.shared.exception.NotLoggedInException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
@SuppressWarnings("serial")
public class EasyMoneyServiceImpl extends RemoteServiceServlet implements EasyMoneyService {

	public LoginInfo login(String requestUri)  {
		LoginInfo login = new LoginInfo();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (null == user)  {
			login.setLoggedIn(false);
			login.setLoginUrl(userService.createLoginURL(requestUri));
		} else  {
			login.setLoggedIn(true);
			login.setEmailAddress(user.getEmail());
			login.setNickname(user.getNickname());
			login.setLogoutUrl(userService.createLogoutURL(requestUri));
		}
		return login;
	}
	
	
	public BudgetTo getActiveBudget() throws NotLoggedInException {
		checkLoggedIn();
		BudgetTo testBudget = new BudgetTo();
		testBudget.setName("Test Budget");
		
		testBudget.setBalance(new MoneyTo(400, 00));
		testBudget.setMonthlySavings(new MoneyTo(100, 00));
		
		List<IncomeTo> incomes = new ArrayList<IncomeTo>();
		IncomeTo income1 = new IncomeTo();
		income1.setAmount(new MoneyTo(2000, 00));
		income1.setFrequency(Frequency.BiWeekly);
		income1.setName("Salary");
		income1.setNextPayDate(new Date());
		
		incomes.add(income1);
		testBudget.setIncomes(incomes);

		List<BillTo> monthlyBills = new ArrayList<BillTo>();
		BillTo bill1 = new BillTo();
		bill1.setReminderActive(false);
		bill1.setDayOfMonth(10);
		bill1.setReminderDay(5);
		bill1.setAmount(new MoneyTo(50, 0));
		bill1.setName("My Monthly bill");
		monthlyBills.add(bill1);
		testBudget.setMonthlyBills(monthlyBills);
		
		List<ExpenseCategoryTo> expenseCategories = new ArrayList<ExpenseCategoryTo>();
		ExpenseCategoryTo expenseCategory1 = new ExpenseCategoryTo();
		expenseCategory1.setAccumulating(false);
		expenseCategory1.setAmount(new MoneyTo(300, 00));
		expenseCategory1.setName("Groceries");
		expenseCategory1.setBalance(new MoneyTo(140, 30));
		expenseCategory1.setFrequencyToRefresh(Frequency.Monthly);
		expenseCategories.add(expenseCategory1);
		
		ExpenseCategoryTo expenseCategory2 = new ExpenseCategoryTo();
		expenseCategory2.setAccumulating(false);
		expenseCategory2.setAmount(new MoneyTo(250, 00));
		expenseCategory2.setName("Fuel");
		expenseCategory2.setBalance(new MoneyTo(102, 65));
		expenseCategory2.setFrequencyToRefresh(Frequency.Monthly);
		expenseCategories.add(expenseCategory2);
		
		testBudget.setCategories(expenseCategories);
		
		return testBudget;
	}
	
	public void saveBudget(BudgetTo budget) throws NotLoggedInException {
		checkLoggedIn();
		
	}
	
	private void checkLoggedIn() throws NotLoggedInException {
		if (null == getUser())  {
			throw new NotLoggedInException();
		}
	}
	
	private User getUser()  {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	
}