package net.switchcase.easymoney.server.dao;

import net.switchcase.easymoney.server.domain.Budget;

import com.google.appengine.api.users.User;

public interface BudgetDao {
	
	Budget findBudget(String id);
	Budget findActiveBudget(User user);
	void saveBudget(Budget budget);

}
