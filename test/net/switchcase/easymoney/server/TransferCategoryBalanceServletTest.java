package net.switchcase.easymoney.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Account;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.shared.AccountType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.users.User;

public class TransferCategoryBalanceServletTest {
	
	@Mock private BudgetDao budgetDao;
	@Mock private TransactionDao txnDao;
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private PrintWriter printWriter;
	
	private TransferCategoryBalanceServlet servlet;
	private Budget budget;
	private ExpenseCategory sourceCategory;
	private ExpenseCategory destinationCategory;
	private Device device = new Device(new User("testuser@switchcase.net", "switchcase.net"));
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		servlet = new TransferCategoryBalanceServlet(budgetDao, txnDao);
		
		when(response.getWriter()).thenReturn(printWriter);

		sourceCategory = new ExpenseCategory();
		destinationCategory = new ExpenseCategory();
		
		
		budget = new Budget();
		budget.setSavingsAccount(new Account("SavingsAccountId", "Savings", AccountType.Savings, 20000L, budget));
		budget.setCategories(Arrays.asList(sourceCategory, destinationCategory));
		sourceCategory.setBudget(budget);
		destinationCategory.setBudget(budget);

		sourceCategory.setAccount(new Account("SourceAccountId", "SourceAccount", AccountType.Expense, 10000L, budget));
		destinationCategory.setAccount(new Account("DestAccountId", "DestinationAccount", AccountType.Expense, 10000L, budget));

		when(budgetDao.findActiveBudget(device.getUser())).thenReturn(budget);
		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);

	}

	private void createValidRequest() {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceAccount")).thenReturn("SourceAccountId");
		when(request.getParameter("destinationAccount")).thenReturn("DestAccountId");
		when(request.getParameter("amount")).thenReturn("4000");
	}

	@Test
	public void testTransferBalanceMovesBalanceFromOneCategoryToAnother() throws Exception {
		
		createValidRequest();
		
		servlet.doPost(request, response);

		assertEquals(14000, (long)destinationCategory.getAccount().getBalance());
		assertEquals(6000, (long)sourceCategory.getAccount().getBalance());
	}

	@Test
	public void testXmlSuccess()  throws Exception {

		createValidRequest();
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>OK</result></results>");
	}
	
	@Test
	public void testTransferLogicChecksDeviceToken() throws Exception  {
		
		createValidRequest();
		
		servlet.doPost(request, response);
		
		verify(budgetDao).findDevice("TestDeviceKey");
	}
	
	@Test
	public void testTransferLogicReturnsErrorWhenDeviceKeyInvalid() throws Exception  {
		
		when(request.getParameter("deviceKey")).thenReturn("Invalid Device Key");
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidDeviceKey</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputWhenInvalidSourceKeyIsUsed() throws Exception  {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceAccount")).thenReturn("InvalidSourceCategoryKey");
		when(request.getParameter("destinationAccount")).thenReturn("DestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidSourceAccount</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputWhenInvalidDestinationKeyIsUsed() throws Exception {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceAccount")).thenReturn("SourceAccountId");
		when(request.getParameter("destinationAccount")).thenReturn("InvalidDestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");

		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidDestinationAccount</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputIfSourceDoesntHaveEnoughFunds() throws Exception  {
		
		createValidRequest();
		
		budget.getSavingsAccount().setBalance(12000L);
		sourceCategory.getAccount().setBalance(2000L);
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InsufficientFunds</result></results>");
	}
}
