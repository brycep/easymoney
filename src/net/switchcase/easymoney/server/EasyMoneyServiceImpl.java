package net.switchcase.easymoney.server;

import java.util.Date;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.JdoBudgetDao;
import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.LoginInfo;
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

	private BudgetDao budgetDao = new JdoBudgetDao();
	final private Converter converter = new Converter();
		
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
		User user = getUser();
		Budget budget = budgetDao.findActiveBudget(user);
		
		if (null == budget)  {
			budget = new Budget();
			budget.setOwner(user);
			budget.setCreateDate(new Date());
			buildTemplateBudget(budget);
		}
		
		budget.setLastAccessed(new Date());
		
		BudgetTo budgetTo = converter.toTransferObject(budget);
		return budgetTo;
	}
	
	public void saveBudget(BudgetTo budgetTo) throws NotLoggedInException {
		checkLoggedIn();
		Budget budget;
		if (null == budgetTo.getId())  {
			budget = new Budget();
			budget.setCreateDate(new Date());
			budget.setLastAccessed(new Date());
			budget.setOwner(getUser());
		} else  {
			budget = budgetDao.findBudget(budgetTo.getId());
		}
		converter.mergeTransferObjectIntoDomain(budget, budgetTo);
		budgetDao.saveBudget(budget);
	}
	
	private void buildTemplateBudget(Budget budget)  {
		Income salary = new Income();
		salary.setName("Salary");
		salary.setFrequency(Frequency.BiWeekly);
		
		Bill phoneBill = new Bill();
		phoneBill.setName("Phone Bill");
		phoneBill.setDayOfMonth(15);
		
		ExpenseCategory groceries = new ExpenseCategory();
		groceries.setName("Groceries");
		groceries.setFrequencyToRefresh(Frequency.BiWeekly);
		groceries.setAccumulating(false);
		
		ExpenseCategory fun = new ExpenseCategory();
		fun.setName("Fun Money");
		fun.setFrequencyToRefresh(Frequency.BiWeekly);
		fun.setAccumulating(true);
		
		budget.getIncomes().add(salary);
		budget.getMonthlyBills().add(phoneBill);
		budget.getCategories().add(groceries);
		budget.getCategories().add(fun);
		
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