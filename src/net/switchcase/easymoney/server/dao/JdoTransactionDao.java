package net.switchcase.easymoney.server.dao;

import javax.jdo.PersistenceManager;

import net.switchcase.easymoney.server.domain.Transaction;

public class JdoTransactionDao implements TransactionDao {
	
	public void addTransaction(Transaction transaction) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(transaction);
		} finally {
			pm.close();
		}
		
	}

}
