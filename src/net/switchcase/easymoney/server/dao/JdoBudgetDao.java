package net.switchcase.easymoney.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.Income;

import com.google.appengine.api.users.User;

public class JdoBudgetDao implements BudgetDao {
	
	public Budget findBudget(String id)  {
		PersistenceManager pm = null;
		Budget budget = null;
		try  {
			pm = PMF.get().getPersistenceManager();
			budget = pm.getObjectById(Budget.class, id);
		} finally {
			pm.close();
		}
		return budget;
	}
	
	@SuppressWarnings("unchecked")
	public Budget findActiveBudget(User user)  {
		PersistenceManager pm = null;
		Budget budget = null;
		try  {
			pm = PMF.get().getPersistenceManager();
			
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
		} finally {
			pm.close();
		}
		return budget;
	}
	
	public void saveBudget(Budget budget) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
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
		} finally {
			pm.close();
		}
	}

	public Device findDevice(String id)  {
		PersistenceManager pm = null;
		Device device = null;
		try  {
			pm = PMF.get().getPersistenceManager();
			device = pm.getObjectById(Device.class, id);
		} finally {
			pm.close();
		}
		return device;
	}

	public void saveDevice(Device device) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(device);
		} finally {
			pm.close();
		}
	}
	
	public CashEnvelope findExpenseCategory(String id)  {
		PersistenceManager pm = null;
		CashEnvelope expenseCategory = null;
		try {
			pm = PMF.get().getPersistenceManager();
			expenseCategory = pm.getObjectById(CashEnvelope.class, id);
		} finally {
			pm.close();
		}
		return expenseCategory;
	}

}
