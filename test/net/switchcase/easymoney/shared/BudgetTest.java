package net.switchcase.easymoney.shared;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetTest {
	
	@Test
	public void testCalculateExpenseTotal()  {
		BudgetTo budget = new BudgetTo();
		ExpenseCategoryTo expense1 = new ExpenseCategoryTo();
		ExpenseCategoryTo expense2 = new ExpenseCategoryTo();
		budget.setCategories(Arrays.asList(expense1, expense2));
		
		expense1.setAmount(new MoneyTo(1, 50));
		expense2.setAmount(new MoneyTo(4, 35));

		assertTrue(new MoneyTo(5, 85).equals(budget.calulateExpenseTotal()));
	}

}
