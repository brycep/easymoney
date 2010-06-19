package net.switchcase.easymoney.server;

import java.util.Date;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.EnvelopeType;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.LoginInfo;
import net.switchcase.easymoney.shared.exception.NotLoggedInException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
public class EasyMoneyServiceImpl implements EasyMoneyService {

	private BudgetDao budgetDao; 
	final private Converter converter = new Converter();
	
	@Inject
	public EasyMoneyServiceImpl(BudgetDao budgetDao)  {
		this.budgetDao = budgetDao;
	}
	
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
		
		budget.setBillsEnvelope(new CashEnvelope("Bills", EnvelopeType.DefaultBills, 0, 0, budget));
		budget.setDefaultSavings(new CashEnvelope("Savings", EnvelopeType.DefaultSavings, 0, 0, budget));
	
		Income salary = new Income();
		salary.setName("Salary");
		salary.setFrequency(Frequency.BiWeekly);
		
		Bill phoneBill = new Bill();
		phoneBill.setName("Phone Bill");
		phoneBill.setDayOfMonth(15);
		
		CashEnvelope groceries = new CashEnvelope("Groceries", EnvelopeType.Expense, 0L, 0L, budget);
		CashEnvelope gas = new CashEnvelope("Gas", EnvelopeType.Expense, 0L, 0L, budget);
		CashEnvelope diningOut = new CashEnvelope("Dining Out", EnvelopeType.Expense, 0L, 0L, budget);
		
		budget.getIncomes().add(salary);
		budget.getMonthlyBills().add(phoneBill);
		budget.addExpense(diningOut);
		budget.addExpense(gas);
		budget.addExpense(groceries);
		
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