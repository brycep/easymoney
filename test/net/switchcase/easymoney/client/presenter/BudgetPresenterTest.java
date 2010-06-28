package net.switchcase.easymoney.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.test.MockTextBox;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.CashEnvelopeTo;
import net.switchcase.easymoney.shared.EnvelopeType;
import net.switchcase.easymoney.shared.MoneyTo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class BudgetPresenterTest {
	
	@Mock private EasyMoneyServiceAsync easyMoneyService;
	@Mock private BudgetPresenter.Display display;
	@Mock private BudgetPresenter.SummaryDisplay summaryDisplay;
	@Mock private BudgetPresenter.ExpenseDisplay expenseDisplay;
	@Mock private BudgetPresenter.IncomeDisplay incomeDisplay;
	@Mock private BudgetPresenter.BillsDisplay billsDisplay;
	@Mock private HasMoneyValue expenseTotalLabel;
	
	@Before
	public void setUp()  {
		MockitoAnnotations.initMocks(this);
		when(display.getSummaryView()).thenReturn(summaryDisplay);
		when(display.getSummaryView().getSharedWith()).thenReturn(new MockTextBox());
		when(display.getExpenseCategoriesView()).thenReturn(expenseDisplay);
		when(display.getIncomeView()).thenReturn(incomeDisplay);
		when(display.getBillsView()).thenReturn(billsDisplay);
	}
	
	// Make sure the onExpenseAmountChanged event recalculates
	// the total and updates the label.
	@Test
	public void testExpenseAmountChanged()  {
		
		when(display.getExpenseCategoriesView()).thenReturn(expenseDisplay);
		when(expenseDisplay.getTotalExpenseLabel()).thenReturn(expenseTotalLabel);
		
		BudgetTo budget = new BudgetTo();
		CashEnvelopeTo expense1 = new CashEnvelopeTo();
		expense1.setType(EnvelopeType.Expense);
		CashEnvelopeTo expense2 = new CashEnvelopeTo();
		expense2.setType(EnvelopeType.Expense);
		budget.getEnvelopes().add(expense1);
		budget.getEnvelopes().add(expense2);
		
		expense1.setAmount(new MoneyTo(1, 50));
		expense2.setAmount(new MoneyTo(4, 35));
		
		BudgetPresenter budgetPresenter = new BudgetPresenter(easyMoneyService, null, display);
		budgetPresenter.setBudget(budget);
		
		budgetPresenter.expenseAmountsChanged();
		
		verify(expenseTotalLabel).setValue(new MoneyTo(5, 85));
	}
	
	@Test
	public void testUpdateModel()  {
		
		when(display.getSummaryView().getSharedWith()).thenReturn(new MockTextBox("SharedWith"));
		
		BudgetTo budget = new BudgetTo();
		
		BudgetPresenter budgetPresenter = new BudgetPresenter(easyMoneyService, null, display);
		budgetPresenter.setBudget(budget);
		
		budgetPresenter.updateModel();
		
		assertEquals("SharedWith", budget.getSharedWith());
		
		verify(display.getIncomeView()).updateModel();
		verify(display.getBillsView()).updateModel();
		verify(display.getExpenseCategoriesView()).updateModel();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testSaveBudget()  {
		BudgetTo budget = new BudgetTo();
		
		BudgetPresenter budgetPresenter = new BudgetPresenter(easyMoneyService, null, display);
		budgetPresenter.setBudget(budget);
		
		budgetPresenter.saveBudget();
		
		verify(display).disableSaveButton();
		verify(easyMoneyService).saveBudget(Mockito.same(budget), (AsyncCallback<BudgetTo>)Mockito.anyObject());
		
	}

}
