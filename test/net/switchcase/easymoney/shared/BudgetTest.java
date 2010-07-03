package net.switchcase.easymoney.shared;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BudgetTest {
	
	@Test
	public void testCalculateExpenseTotal()  {
		BudgetTo budget = new BudgetTo();
		CashEnvelopeTo expense1 = new CashEnvelopeTo();
		expense1.setType(EnvelopeType.Expense);
		CashEnvelopeTo expense2 = new CashEnvelopeTo();
		expense2.setType(EnvelopeType.Expense);

		budget.setEnvelopes(Arrays.asList(expense1, expense2));
		
		expense1.setAmount(new MoneyTo(1, 50));
		expense2.setAmount(new MoneyTo(4, 35));

		assertEquals(new MoneyTo(5, 85), budget.calculateExpenseAmountTotal());
	}
	
	@Test
	public void testCalculateIncomeTotal()  {
		BudgetTo budget = new BudgetTo();
		IncomeTo income1 = new IncomeTo();
		IncomeTo income2 = new IncomeTo();
		budget.setIncomes(Arrays.asList(income1, income2));
		
		income1.setAmount(new MoneyTo(300, 80));
		income2.setAmount(new MoneyTo(50, 25));
		
		assertEquals(new MoneyTo(351, 05), budget.calculateIncomeTotal());
	}
	
	@Test
	public void testCalculateBillTotal()  {
		BudgetTo budget = new BudgetTo();
		BillTo bill1 = new BillTo();
		BillTo bill2 = new BillTo();
		budget.setMonthlyBills(Arrays.asList(bill1, bill2));
		
		bill1.setAmount(new MoneyTo(230, 75));
		bill2.setAmount(new MoneyTo(50, 20));
		
		assertEquals(new MoneyTo(280, 95), budget.calculateBillTotal());
	}
	
	@Test
	public void testCalculateExpenseTotalReturnsZeroIfNullExpenseList()  {
		BudgetTo budget = new BudgetTo();
		budget.setEnvelopes(null);
		
		assertEquals(new MoneyTo(0, 0), budget.calculateExpenseAmountTotal());
	}

}
