package net.switchcase.easymoney.server.dao;

import net.switchcase.easymoney.server.domain.Transaction;

public interface TransactionDao {
	
	void addTransaction(Transaction transaction);

}
