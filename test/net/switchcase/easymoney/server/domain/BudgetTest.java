package net.switchcase.easymoney.server.domain;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import net.switchcase.easymoney.shared.EnvelopeType;
import net.switchcase.easymoney.shared.Frequency;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BudgetTest {

	private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	private Budget budget = new Budget();
	private Income income = new Income();
	private Bill ccBill = new Bill();
	private Bill phoneBill = new Bill();
	private Bill mortgageBill = new Bill();
	private CashEnvelope savings;
	private CashEnvelope bills;
	private CashEnvelope groceries; 
	private CashEnvelope misc;

	@Before
	public void setUp() throws Exception {
				
		income.setAmount(100000L);
		income.setFrequency(Frequency.BiWeekly);
		income.setName("Salary");
		income.setNextPayDate(formatter.parse("01-01-2010"));
		budget.setIncomes(Arrays.asList(income));
		
		ccBill.setAmount(20000L);
		ccBill.setName("Credit Card Bill");
		ccBill.setNextDueDate(formatter.parse("01-02-2010"));
		
		phoneBill.setAmount(4500L);
		phoneBill.setName("Phone bill");
		phoneBill.setNextDueDate(formatter.parse("01-09-2010"));
		
		mortgageBill.setAmount(40000L);
		mortgageBill.setName("Mortgage");
		mortgageBill.setNextDueDate(formatter.parse("01-25-2010"));
		
		budget.setMonthlyBills(Arrays.asList(ccBill, phoneBill, mortgageBill));
		
		savings = new CashEnvelope("Savings", EnvelopeType.DefaultSavings, 0, 2000, budget);
		bills = new CashEnvelope("Bills", EnvelopeType.DefaultBills, 0, 0, budget);
		groceries = new CashEnvelope("Groceries", EnvelopeType.Expense, 10000, 1000, budget);
		misc = new CashEnvelope("Misc", EnvelopeType.Expense, 20000, 1000, budget);

		budget.getEnvelopes().add(savings);
		budget.setBillsEnvelope(bills);
		budget.addExpense(groceries);
		budget.addExpense(misc);
	}
	
	@Test
	public void testPaydayIncreasesExpenses() throws Exception {
		budget.payday(formatter.parse("01-01-2010"));
		
		assertEquals(11000, groceries.getBalance());
		assertEquals(21000, misc.getBalance());
	}
	
	@Test
	public void testPaydayCalculatesBills() throws Exception {
		budget.payday(formatter.parse("01-01-2010"));
	
		assertTrue(ccBill.isDue());
		assertTrue(phoneBill.isDue());
		
		assertEquals(24500, bills.getBalance());
	}
	
	@Test
	public void testPaydayAutomaticallyPutsLeftoverInSavings() throws Exception  {
		budget.payday(formatter.parse("01-01-2010"));
		
		assertEquals(47500, savings.getBalance());
	}
	
	@Test
	public void testPaydayDoesNothingIfNotPaidToday() throws Exception {
		budget.payday(formatter.parse("01-03-2010"));
		assertEquals(2000, savings.getBalance());
	
	}
	

}
