package net.switchcase.easymoney.server.dao;

import java.util.List;

import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.Transaction;

import com.google.appengine.api.users.User;

public interface BudgetDao {
	
	Budget findBudget(String id);
	Budget findActiveBudget(User user);
	void saveBudget(Budget budget);
	
	CashEnvelope findExpenseCategory(String id);
	
	Device findDevice(String id);
	void saveDevice(Device device);
	
	List<Transaction> findTransactions(Budget budget, int days, String timeZone);

}
