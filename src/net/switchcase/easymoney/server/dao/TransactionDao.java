package net.switchcase.easymoney.server.dao;

import javax.jdo.PersistenceManager;

import net.switchcase.easymoney.server.domain.Transaction;
import net.switchcase.easymoney.server.domain.Transfer;

public interface TransactionDao {
	
	void addTransaction(Transaction transaction, PersistenceManager pm);
	void addTransfer(Transfer transfer, PersistenceManager pm);

}
