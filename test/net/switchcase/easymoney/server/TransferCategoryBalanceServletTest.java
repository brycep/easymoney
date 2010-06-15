package net.switchcase.easymoney.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.shared.EnvelopeType;

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
	private CashEnvelope sourceEnvelope;
	private CashEnvelope destinationEnvelope;
	private Device device = new Device(new User("testuser@switchcase.net", "switchcase.net"));
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		servlet = new TransferCategoryBalanceServlet(budgetDao, txnDao);
		
		when(response.getWriter()).thenReturn(printWriter);		
		
		budget = new Budget();
		sourceEnvelope = new CashEnvelope("Source", EnvelopeType.Expense, 0, 20000L, budget);
		sourceEnvelope.setId("SourceId");
		destinationEnvelope = new CashEnvelope("Dest", EnvelopeType.Expense, 0, 20000L, budget);
		destinationEnvelope.setId("DestId");
		budget.addExpense(sourceEnvelope);
		budget.addExpense(destinationEnvelope);

		when(budgetDao.findActiveBudget(device.getUser())).thenReturn(budget);
		when(budgetDao.findDevice("TestDeviceKey")).thenReturn(device);

	}

	private void createValidRequest() {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceEnvelope")).thenReturn("SourceId");
		when(request.getParameter("destinationEnvelope")).thenReturn("DestId");
		when(request.getParameter("amount")).thenReturn("4000");
	}

	@Test
	public void testTransferBalanceMovesBalanceFromOneCategoryToAnother() throws Exception {
		
		createValidRequest();
		
		servlet.doPost(request, response);

		assertEquals(24000, (long)destinationEnvelope.getBalance());
		assertEquals(16000, (long)sourceEnvelope.getBalance());
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
		when(request.getParameter("sourceEnvelope")).thenReturn("InvalidSourceCategoryKey");
		when(request.getParameter("destinationEnvelope")).thenReturn("DestinationCategoryKey");
		when(request.getParameter("amount")).thenReturn("4000");
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidSourceEnvelope</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputWhenInvalidDestinationKeyIsUsed() throws Exception {
		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
		when(request.getParameter("sourceEnvelope")).thenReturn("SourceId");
		when(request.getParameter("destinationEnvelope")).thenReturn("InvalidDestinationEnvelopeKey");
		when(request.getParameter("amount")).thenReturn("4000");

		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InvalidDestinationEnvelope</result></results>");
	}
	
	@Test
	public void testErrorXmlIsOutputIfSourceDoesntHaveEnoughFunds() throws Exception  {
		
		createValidRequest();
		
		sourceEnvelope.setBalance(2000L);
		
		servlet.doPost(request, response);
		
		verify(printWriter).print("<results><result>InsufficientFunds</result></results>");
	}
}
