package net.switchcase.easymoney.server.dao;

import net.switchcase.easymoney.server.domain.Transaction;
import net.switchcase.easymoney.server.domain.Transfer;

public interface TransactionDao {
	
	void addTransaction(Transaction transaction);
	void addTransfer(Transfer transfer);

}
