package net.switchcase.easymoney.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.server.domain.Transaction;

import com.google.appengine.api.users.User;

@SuppressWarnings("unchecked")
public class JdoBudgetDao implements BudgetDao {
	
	public Budget findBudget(String id, PersistenceManager pm)  {
		return pm.getObjectById(Budget.class, id);
	}
	
	public List<Budget> findAllBudgets(PersistenceManager pm)  {
		Query query = pm.newQuery(
				"select from net.switchcase.easymoney.server.domain.Budget ");
		return (List<Budget>) query.execute();
	}
	
	public List<Transaction> findTransactions(Budget budget, int days, String timeZone, PersistenceManager pm)  {
		Date limitDate = calculateLimitDate(days, timeZone, pm);
		
		Query query = pm.newQuery(
				"select from net.switchcase.easymoney.server.domain.Transaction " +
				"where budgetKey == budgetKeyParam and transactionDate > transactionDateParam " +
				"parameters String budgetKeyParam, java.util.Date transactionDateParam");
		return (List<Transaction>) query.execute(budget.getId(), limitDate);
	}
	
	private Date calculateLimitDate(int days, String timeZone, PersistenceManager pm)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR, 0);
		cal.setTimeZone(TimeZone.getTimeZone(timeZone));
		cal.add(Calendar.DATE, (days * -1));
		return cal.getTime();
	}
	
	public Budget findActiveBudget(User user, PersistenceManager pm)  {
		Budget budget = null;
		Query query = pm.newQuery(
				"select from net.switchcase.easymoney.server.domain.Budget " +
				"where owner == ownerParam " +
				"parameters com.google.appengine.api.users.User ownerParam ");
		List<Budget> budgets = (List<Budget>) query.execute(user);
		if ((null == budgets) || (0 == budgets.size()))  {
			query = pm.newQuery(
					"select from net.switchcase.easymoney.server.domain.Budget " +
					"where sharedWith == sharedWithParam " +
					"parameters String sharedWithParam ");
			
			budgets = (List<Budget>) query.execute(user.getEmail());
		}
			
		if ((null != budgets) && (0 != budgets.size()))  {
			budget = budgets.iterator().next();
		}
		return budget;
	}
	
	public void saveBudget(Budget budget, PersistenceManager pm) {
		pm.makePersistent(budget);
		for(Income income : budget.getIncomes())  {
			pm.makePersistent(income);
		}
		for(Bill bill : budget.getMonthlyBills())  {
			pm.makePersistent(bill);
		}
		for(CashEnvelope envelope : budget.getEnvelopes())  {
			pm.makePersistent(envelope);
		}
	}

	public Device findDevice(String id, PersistenceManager pm)  {
		return pm.getObjectById(Device.class, id);
	}

	public void saveDevice(Device device, PersistenceManager pm) {
		pm.makePersistent(device);
	}
	
	public CashEnvelope findExpenseCategory(String id, PersistenceManager pm)   {
		return pm.getObjectById(CashEnvelope.class, id);
	}

}
