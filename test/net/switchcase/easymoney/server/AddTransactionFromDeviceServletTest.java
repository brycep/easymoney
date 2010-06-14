package net.switchcase.easymoney.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Account;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.server.domain.Transaction;
import net.switchcase.easymoney.shared.AccountType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.users.User;



//@SuppressWarnings("unused")
public class AddTransactionFromDeviceServletTest {
	
	@Mock private TransactionDao transactionDao;
	@Mock private BudgetDao budgetDao;
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private PrintWriter printWriter;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private AddTransactionFromDeviceServlet servlet;
	
	private Budget budget = new Budget();
	private ExpenseCategory testExpenseCategory;
	private Account testExpenseAccount;
	private Account checkingAccount;
	private Account expensesAccount;

	@Before
	public void setUp() throws IOException  {
		
		MockitoAnnotations.initMocks(this);
		
		when(response.getWriter()).thenReturn(printWriter);
		servlet = new AddTransactionFromDeviceServlet(transactionDao,
					budgetDao);
		
		User testUser = new User("testemail@switchcase.net", "switchcase.net");
		Device device = new Device(testUser);

		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);
		
		Account savings = new Account("Savings", AccountType.Savings, 100000L, budget);
		budget.setSavingsAccount(savings);   // $1000.00
		
		testExpenseCategory = new ExpenseCategory();
		testExpenseCategory.setId("ExpenseCategoryId1");
		testExpenseCategory.setAmount(35000L);  // $350.00
		testExpenseCategory.setName("Test Expense Category");
		testExpenseCategory.setBudget(budget);
		
		testExpenseAccount = new Account("ExpenseAccountId1", "Expense", AccountType.Expense, 25000L, budget);
		testExpenseCategory.setAccount(testExpenseAccount); 
		
		budget.setCategories(Arrays.asList(testExpenseCategory));
		
		checkingAccount = new Account("CheckingAccountId", "Checking", AccountType.CheckingAccount, 100000L, budget);
		budget.setCheckingAccount(checkingAccount);
		
		expensesAccount = new Account("ExpensesAccountId", "Expenses", AccountType.Expense, 0L, budget);
		budget.setExpenseSpendingAccount(expensesAccount);
		
		when(budgetDao.findActiveBudget(testUser)).thenReturn(budget);
	}
	
	@Test
	public void testAddTransaction() throws Exception  {
		
		createServletParameters();
		servlet.doPost(request, response);
		
		ArgumentCaptor<Transaction> txnParam = ArgumentCaptor.forClass(Transaction.class); 
		verify(transactionDao).addTransaction(txnParam.capture());
		
		Transaction txn = txnParam.getValue();
		assertEquals("Test Description", txn.getDescription());
		assertEquals(11.1111, txn.getGpsLat(), 0.0001);
		assertEquals(22.2222, txn.getGpsLong(), 0.0001);
		assertEquals(3333, (long)txn.getAmount());
		assertEquals("Test Source", txn.getSource());
		
		Date testTimestamp = format.parse("2010-01-01 09:12:34");
		Date testDate = format.parse("2010-02-02 10:00:00");
		assertEquals(testTimestamp, txn.getCreateTimestamp());
		assertEquals(testDate, txn.getTransactionDate());
		
		assertFalse(txn.isReconsiled());
		assertEquals("ExpenseAccountId1", txn.getDebitAccountKey());
	}

	@Test
	public void testXmlSuccess()  throws Exception {
		createServletParameters();
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>OK</result></results>");
	}
	
	@Test
	public void testAddTransactionLogicChecksDeviceToken() throws Exception  {
		createServletParameters();
		
		servlet.doPost(request, response);
		
		verify(budgetDao).findDevice("TestDeviceKey");
	}
	
	@Test
	public void testAddTransactionLogicReturnsErrorWhenDeviceKeyInvalid() throws Exception  {
		createServletParameters();

		when(request.getParameter("deviceKey")).thenReturn("Nothing");
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidDeviceKey</result></results>");
	}
	
	@Test
	public void testAddTransactionAppliesAmountToBalance() throws Exception {
		createServletParameters();
		servlet.doPost(request, response);
		
		// The system should have reduced the Test Expense Category balance by $33.33
		assertEquals(21667, (long) testExpenseAccount.getBalance());
	}
	
	@Test
	public void testAddTransactionAppliesAmountToBudgetBalance() throws Exception  {
		createServletParameters();
		servlet.doPost(request, response);
		
		// The system should have reduced the total budget balance by $33.33
		assertEquals(3, (long) budget.getExpenseSpendingAccount().getBalance());
	}

	@Test
	public void testAddTransactionReportsErrorIfExpenseCategoryNotFound() throws Exception  {
		createServletParameters();
		when(request.getParameter("expenseCategoryKey")).thenReturn("Invalid Expense Key");
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>ExpenseCategoryNotFound</result></results>");
	}
	
	@Test
	public void testSpendingMoreThanWhatYouHaveCausesError()  throws Exception {
		testExpenseCategory.getAccount().setBalance(1000L);
		
		createServletParameters();
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InsufficientBalanceInCategory</result></results>");
	}

	private void createServletParameters() {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("description")).thenReturn("Test Description");
		when(request.getParameter("gpsLat")).thenReturn("11.1111");
		when(request.getParameter("gpsLong")).thenReturn("22.2222");
		when(request.getParameter("amount")).thenReturn("3333");
		when(request.getParameter("source")).thenReturn("Test Source");
		when(request.getParameter("createTimestamp")).thenReturn("2010-01-01 09:12:34");
		when(request.getParameter("transactionDate")).thenReturn("2010-02-02 10:00:00");
		when(request.getParameter("reconsiled")).thenReturn("false");
		when(request.getParameter("expenseCategoryKey")).thenReturn("ExpenseCategoryId1");
	}
	
}
