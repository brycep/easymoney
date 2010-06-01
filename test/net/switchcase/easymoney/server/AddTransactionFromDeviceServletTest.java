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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.server.domain.Transaction;

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

	@Before
	public void setUp() throws IOException  {
		
		MockitoAnnotations.initMocks(this);
		
		when(response.getWriter()).thenReturn(printWriter);
		servlet = new AddTransactionFromDeviceServlet(transactionDao,
					budgetDao, request, response);
		
		User testUser = new User("testemail@switchcase.net", "switchcase.net");
		Device device = new Device(testUser);

		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);
		
		budget.setBalance(100000L);   // $1000.00
		
		testExpenseCategory = new ExpenseCategory();
		testExpenseCategory.setId("ExpenseCategoryId1");
		testExpenseCategory.setAmount(35000L);  // $350.00
		testExpenseCategory.setBalance(25000L);  // $250.00
		testExpenseCategory.setName("Test Expense Category");
		testExpenseCategory.setBudget(budget);
		budget.setCategories(Arrays.asList(testExpenseCategory));
		
		when(budgetDao.findExpenseCategory("ExpenseCategoryId1")).thenReturn(testExpenseCategory);
	}
	
	@Test
	public void testAddTransaction() throws Exception  {
		
		Map<String, String[]> params = createServletParameters();
		servlet.doPost(params);
		
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
		assertEquals("ExpenseCategoryId1", txn.getExpenseCategoryKey());
	}

	@Test
	public void testXmlSuccess()  throws Exception {
		Map<String, String[]> params = createServletParameters();
		
		servlet.doPost(params);
		
		verify(printWriter).print("<results><result>OK</result></results>");
	}
	
	@Test
	public void testAddTransactionLogicChecksDeviceToken() throws Exception  {
		Map<String, String[]> params = createServletParameters();
		
		servlet.doPost(params);
		
		verify(budgetDao).findDevice("TestDeviceKey");
	}
	
	@Test
	public void testAddTransactionLogicReturnsErrorWhenDeviceKeyInvalid() throws Exception  {
		Map<String, String[]> params = createServletParameters();

		params.put("deviceKey", new String[] {"Nothing"});
		
		servlet.doPost(params);
		
		verify(printWriter).print("<results><result>InvalidDeviceKey</result></results>");
	}
	
	@Test
	public void testAddTransactionAppliesAmountToBalance() throws Exception {
		Map<String, String[]> params = createServletParameters();
		servlet.doPost(params);
		
		// The system should have reduced the Test Expense Category balance by $33.33
		assertEquals(21667, (long) testExpenseCategory.getBalance());
	}
	
	@Test
	public void testAddTransactionAppliesAmountToBudgetBalance() throws Exception  {
		Map<String, String[]> params = createServletParameters();
		servlet.doPost(params);
		
		// The system should have reduced the total budget balance by $33.33
		assertEquals(96667, (long) budget.getBalance());
	}

	@Test
	public void testAddTransactionReportsErrorIfExpenseCategoryNotFound() throws Exception  {
		Map<String, String[]> params = createServletParameters();
		params.put("expenseCategoryKey", new String[] {"Invalid Expense Key"});
		servlet.doPost(params);
		
		verify(printWriter).print("<results><result>ExpenseCategoryNotFound</result></results>");
	}
	
	@Test
	public void testSpendingMoreThanWhatYouHaveCausesError()  throws Exception {
		testExpenseCategory.setBalance(1000L);
		
		Map<String, String[]> params = createServletParameters();
		servlet.doPost(params);
		
		verify(printWriter).print("<results><result>InsufficientBalanceInCategory</result></results>");
	}

	private Map<String, String[]> createServletParameters() {
		Map<String, String[]> params = new HashMap<String, String[]>();
		
		params.put("deviceKey", new String[] {"TestDeviceKey"});
		params.put("description", new String[] {"Test Description"});
		params.put("gpsLat", new String[] {"11.1111"});
		params.put("gpsLong", new String[] {"22.2222"});
		params.put("amount", new String[] {"3333"});
		params.put("source", new String[] {"Test Source"});
		params.put("createTimestamp", new String[] {"2010-01-01 09:12:34"});
		params.put("transactionDate", new String[] {"2010-02-02 10:00:00"});
		params.put("reconsiled", new String[] {"false"});
		params.put("expenseCategoryKey", new String[] {"ExpenseCategoryId1"});
		return params;
	}
	
}
