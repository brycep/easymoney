package net.switchcase.easymoney.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.users.User;

public class TransferCategoryBalanceServletTest {
	
	@Mock private BudgetDao budgetDao;
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
		
		servlet = new TransferCategoryBalanceServlet(budgetDao);
		
		when(response.getWriter()).thenReturn(printWriter);

		sourceCategory = new ExpenseCategory();
		destinationCategory = new ExpenseCategory();
		
		sourceCategory.setBalance(10000L);
		destinationCategory.setBalance(10000L);
		
		budget = new Budget();
		budget.setBalance(20000L);
		budget.setCategories(Arrays.asList(sourceCategory, destinationCategory));
		sourceCategory.setBudget(budget);
		destinationCategory.setBudget(budget);

		when(budgetDao.findExpenseCategory("SourceCategoryKey")).thenReturn(sourceCategory);
		when(budgetDao.findExpenseCategory("DestinationCategoryKey")).thenReturn(destinationCategory);
		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);

	}

	private void createValidRequest() {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceCategory")).thenReturn("SourceCategoryKey");
		when(request.getParameter("destinationCategory")).thenReturn("DestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");
	}

	@Test
	public void testTransferBalanceMovesBalanceFromOneCategoryToAnother() throws Exception {
		
		createValidRequest();
		
		servlet.doPost(request, response);

		assertEquals(14000, (long)destinationCategory.getBalance());
		assertEquals(6000, (long)sourceCategory.getBalance());
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
		when(request.getParameter("sourceCategory")).thenReturn("InvalidSourceCategoryKey");
		when(request.getParameter("destinationCategory")).thenReturn("DestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidSourceCategory</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputWhenInvalidDestinationKeyIsUsed() throws Exception {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceCategory")).thenReturn("SourceCategoryKey");
		when(request.getParameter("destinationCategory")).thenReturn("InvalidDestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");

		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidDestinationCategory</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputIfSourceDoesntHaveEnoughFunds() throws Exception  {
		
		createValidRequest();
		
		budget.setBalance(12000L);
		sourceCategory.setBalance(2000L);
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>NotEnoughMoneyInSourceCategory</result></results>");
	}
}
