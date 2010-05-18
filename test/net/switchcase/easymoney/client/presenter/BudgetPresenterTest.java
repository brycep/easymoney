package net.switchcase.easymoney.client.presenter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.MoneyTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BudgetPresenterTest {
	
	@Mock private EasyMoneyServiceAsync easyMoneyService;
	@Mock private BudgetPresenter.Display display;
	@Mock private BudgetPresenter.ExpenseDisplay expenseDisplay;
	@Mock private HasMoneyValue expenseTotalLabel;
	
	@Before
	public void setUp()  {
		MockitoAnnotations.initMocks(this);
	}
	
	// Make sure the onExpenseAmountChanged event recalculates
	// the total and updates the label.
	@Test
	public void testExpenseAmountChanged()  {
		
		when(display.getExpenseCategoriesView()).thenReturn(expenseDisplay);
		when(expenseDisplay.getTotalExpenseLabel()).thenReturn(expenseTotalLabel);
		
		BudgetTo budget = new BudgetTo();
		ExpenseCategoryTo expense1 = new ExpenseCategoryTo();
		ExpenseCategoryTo expense2 = new ExpenseCategoryTo();
		budget.setCategories(Arrays.asList(expense1, expense2));
		
		expense1.setAmount(new MoneyTo(1, 50));
		expense2.setAmount(new MoneyTo(4, 35));
		
		BudgetPresenter budgetPresenter = new BudgetPresenter(easyMoneyService, null, display);
		budgetPresenter.setBudget(budget);
		
		budgetPresenter.expenseAmountsChanged();
		
		verify(expenseTotalLabel).setValue(new MoneyTo(5, 85));
	}

}
