package net.switchcase.easymoney.server.domain;

import net.switchcase.easymoney.shared.AccountType;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	
	@Test
	public void testAddDebitToExpenseAccountDecreasesBalance() throws Exception {
		Account account = new Account("My new account", AccountType.Expense, 20000L, null);
		account.addDebit(10000L);
		
		assertEquals(10000L, account.getBalance());
	}
	
	@Test
	public void testAddDebitToCheckingAccountIncreasesBalance() throws Exception {
		Account account = new Account("My new account", AccountType.CheckingAccount, 20000L, null);
		account.addDebit(8500L);
		
		assertEquals(28500L, account.getBalance());
	}
	
	@Test
	public void testAddDebitToSavingsAccountDecreasesBalance() throws Exception {
		Account account = new Account("My new account", AccountType.Savings, 20000L, null);
		account.addDebit(8500L);
		
		assertEquals(11500L, account.getBalance());
		
	}

	@Test
	public void testAddCreditToExpenseAccountIncreasesBalance() throws Exception {
		Account account = new Account("My new account", AccountType.Expense, 20000L, null);
		account.addCredit(8500L);
		
		assertEquals(28500L, account.getBalance());
	}
	
	@Test
	public void testAddCreditToCheckingAccountDecreasesBalance() throws Exception {
		Account account = new Account("My new account", AccountType.CheckingAccount, 20000L, null);
		account.addCredit(8500L);
		
		assertEquals(11500L, account.getBalance());
	}
	
	@Test
	public void testAddCreditToSavingsAccountIncreasesBalance()  throws Exception {
		Account account = new Account("My new account", AccountType.Savings, 20000L, null);
		account.addCredit(8500L);
		
		assertEquals(28500L, account.getBalance());
	}
}
