package net.switchcase.easymoney.server.dao;

import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;

import com.google.appengine.api.users.User;

public interface BudgetDao {
	
	Budget findBudget(String id);
	Budget findActiveBudget(User user);
	void saveBudget(Budget budget);
	
	ExpenseCategory findExpenseCategory(String id);
	
	Device findDevice(String id);
	void saveDevice(Device device);

}
