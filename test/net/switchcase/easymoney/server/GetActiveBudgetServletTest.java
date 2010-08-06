package net.switchcase.easymoney.server;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.shared.EnvelopeType;
import net.switchcase.easymoney.shared.Frequency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.users.User;

public class GetActiveBudgetServletTest {
	
	@Mock private BudgetDao budgetDao;
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private PrintWriter writer;
	@Mock private PersistenceManagerProvider pmProvider;
	@Mock private PersistenceManager pm;
	
	private GetActiveBudgetServlet servlet;
	private Budget budget = new Budget();
	private Device device;
	private User testUser;
	
	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		when(pmProvider.getPersistenceManager()).thenReturn(pm);
		
		budget.setCreateDate(new Date(2000));
		budget.setId("100");
		budget.setLastAccessed(new Date(3000));
		budget.setName("Test Name");
		
		CashEnvelope expenseEnvelope = buildExpenseEnvelope();
		Bill bill = buildBill();
		Income income = buildIncome();
		
		expenseEnvelope.setBudget(budget);
		budget.addExpense(expenseEnvelope);
		budget.setIncomes(Arrays.asList(income));
		budget.setMonthlyBills(Arrays.asList(bill));
		budget.setDefaultSavings(new CashEnvelope("Savings", EnvelopeType.DefaultSavings, 100L, 200L, budget));
		budget.getDefaultSavings().setId("DefaultSavingsId");
		budget.setBillsEnvelope(new CashEnvelope("Bills", EnvelopeType.DefaultBills, 200L, 300L, budget));
		budget.getBillsEnvelope().setId("BillsEnvelopeId");
		
		testUser = new User("test@switchcase.net", "switchcase.net");
		device = new Device(testUser);
		
		servlet = new GetActiveBudgetServlet(budgetDao, pmProvider);
		
		when(budgetDao.findDevice("TestDeviceKey", pm)).thenReturn(device);
		when(budgetDao.findActiveBudget(testUser, pm)).thenReturn(budget);
		when(response.getWriter()).thenReturn(writer);
	}

	private CashEnvelope buildExpenseEnvelope()  {
		CashEnvelope expenseCategory = new CashEnvelope();
		expenseCategory.setAmount(5000L);
		expenseCategory.setBudget(budget);
		expenseCategory.setId("200");
		expenseCategory.setName("Test Expense Category");
		expenseCategory.setType(EnvelopeType.Expense);
		return expenseCategory;
	}
	
	private Bill buildBill()  {
		Bill bill = new Bill();
		bill.setId("300");
		bill.setAmount(1000L);
		bill.setDayOfMonth(10);
		bill.setName("Test Bill");
		bill.setNextDueDate(new Date(3000));
		return bill;
	}
	
	private Income buildIncome()  {
		Income income = new Income();
		income.setId("300");
		income.setAmount(100000L);
		income.setFrequency(Frequency.Weekly);
		income.setName("Test Income");
		income.setNextPayDate(new Date(4000));
		return income;
	}
	
	@Test
	public void testGetBudget() throws Exception {
		
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");

		servlet.doPost(request, response);
		
		verify(budgetDao).findActiveBudget(testUser, pm);
		
	}
	
	
}
