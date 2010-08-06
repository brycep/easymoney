package net.switchcase.easymoney.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.Transaction;

import com.google.appengine.api.users.User;

public interface BudgetDao {
	
	Budget findBudget(String id, PersistenceManager pm);
	Budget findActiveBudget(User user, PersistenceManager pm);
	List<Budget> findAllBudgets(PersistenceManager pm);
	void saveBudget(Budget budget, PersistenceManager pm);
	
	CashEnvelope findExpenseCategory(String id, PersistenceManager pm);
	
	Device findDevice(String id, PersistenceManager pm);
	void saveDevice(Device device, PersistenceManager pm);
	
	List<Transaction> findTransactions(Budget budget, int days, String timeZone, PersistenceManager pm);

}
