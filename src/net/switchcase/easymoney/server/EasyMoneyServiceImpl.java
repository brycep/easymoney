package net.switchcase.easymoney.server;

import java.util.Date;

import javax.jdo.PersistenceManager;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
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
	private PersistenceManagerProvider pmProvider;
	final private Converter converter = new Converter();
	
	@Inject
	public EasyMoneyServiceImpl(BudgetDao budgetDao,
							    PersistenceManagerProvider pmProvider)  {
		this.budgetDao = budgetDao;
		this.pmProvider = pmProvider;
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
		
		PersistenceManager pm = null;
		BudgetTo budgetTo = null;
		try  {
			pm = pmProvider.getPersistenceManager();
			Budget budget = budgetDao.findActiveBudget(user, pm);
			
			if (null == budget)  {
				budget = new Budget();
				budget.setOwner(user);
				budget.setCreateDate(new Date());
				buildTemplateBudget(budget);
			}
			
			budget.setLastAccessed(new Date());
			
			budgetTo = converter.toTransferObject(budget);
		} finally {
			pm.close();
		}
		return budgetTo;
	}
	
	public BudgetTo saveBudget(BudgetTo budgetTo) throws NotLoggedInException {
		PersistenceManager pm = null;
		try  {
			pm = pmProvider.getPersistenceManager();
			
			checkLoggedIn();
			Budget budget;
			if (null == budgetTo.getId())  {
				budget = new Budget();
				budget.setCreateDate(new Date());
				budget.setLastAccessed(new Date());
				budget.setOwner(getUser());
			} else  {
				budget = budgetDao.findBudget(budgetTo.getId(), pm);
			}
			
			converter.mergeTransferObjectIntoDomain(budget, budgetTo);
			budgetDao.saveBudget(budget, pm);
		} finally {
			pm.close();
		}
		return getActiveBudget();
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
		
		CashEnvelope groceries = new CashEnvelope("Groceries", EnvelopeType.Expense, 10000L, 10000L, budget);
		CashEnvelope gas = new CashEnvelope("Gas", EnvelopeType.Expense, 12000L, 12000L, budget);
		CashEnvelope diningOut = new CashEnvelope("Dining Out", EnvelopeType.Expense, 22000L, 22000L, budget);
		
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