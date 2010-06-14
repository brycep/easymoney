package net.switchcase.easymoney.server;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Account;
import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.shared.AccountType;
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
	
	private GetActiveBudgetServlet servlet;
	private Budget budget = new Budget();
	private Device device;
	private User testUser;
	
	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		budget.setCreateDate(new Date(2000));
		budget.setId("100");
		budget.setLastAccessed(new Date(3000));
		budget.setMonthlySavings(3000L);
		budget.setName("Test Name");
		budget.setSavingsAccount(new Account("Savings", AccountType.Savings, 4000L, budget));
		budget.setCheckingAccount(new Account("Checking", AccountType.CheckingAccount, 2000L, budget));
		budget.setBillsAccount(new Account("BillsAccount", AccountType.Expense, 10000L, budget));
		
		ExpenseCategory expenseCategory = buildExpenseCategory();
		Bill bill = buildBill();
		Income income = buildIncome();
		
		expenseCategory.setBudget(budget);
		budget.setCategories(Arrays.asList(expenseCategory));
		budget.setIncomes(Arrays.asList(income));
		budget.setMonthlyBills(Arrays.asList(bill));
		
		testUser = new User("test@switchcase.net", "switchcase.net");
		device = new Device(testUser);
		
		servlet = new GetActiveBudgetServlet(budgetDao);
		
		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);
		when(budgetDao.findActiveBudget(testUser)).thenReturn(budget);
		when(response.getWriter()).thenReturn(writer);
	}

	private ExpenseCategory buildExpenseCategory()  {
		ExpenseCategory expenseCategory = new ExpenseCategory();
		expenseCategory.setAccumulating(true);
		expenseCategory.setAmount(5000L);
		expenseCategory.setAccount( new Account("TestExpenseAcctId1", "Test Expense Account", AccountType.Expense, 6000L, budget));
		expenseCategory.setBudget(budget);
		expenseCategory.setFrequencyToRefresh(Frequency.BiWeekly);
		expenseCategory.setId("200");
		expenseCategory.setName("Test Expense Category");
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
		
		verify(budgetDao).findActiveBudget(testUser);
		
	}
	
	
}
