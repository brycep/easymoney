package net.switchcase.easymoney.server.dao;

import javax.jdo.PersistenceManager;

import net.switchcase.easymoney.server.domain.Transaction;
import net.switchcase.easymoney.server.domain.Transfer;

public class JdoTransactionDao implements TransactionDao {
	
	public void addTransaction(Transaction transaction, PersistenceManager pm) {
		pm.makePersistent(transaction);
	}

	public void addTransfer(Transfer transfer, PersistenceManager pm)  {
		pm.makePersistent(transfer);
	}
}
