package net.switchcase.easymoney.server;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.appengine.api.users.User;

public class GetRecentTransactionsServletTest {
	
	@Mock private BudgetDao budgetDao;
	@Mock private PersistenceManagerProvider pmProvider;
	@Mock private PersistenceManager pm;
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
		
		when(pmProvider.getPersistenceManager()).thenReturn(pm);
				
		testUser = new User("test@switchcase.net", "switchcase.net");
		device = new Device(testUser);
		
		servlet = new GetActiveBudgetServlet(budgetDao, pmProvider);
		
		when(budgetDao.findDevice("TestDeviceKey", pm)).thenReturn(device);
		when(budgetDao.findActiveBudget(testUser, pm)).thenReturn(budget);
		when(response.getWriter()).thenReturn(writer);
	}

	@Test
	public void testGetTransactions() throws Exception {

		// uncomment these when we work on this service
//		when(request.getParameter("deviceKey")).thenReturn("TestDeviceKey");
//		when(request.getParameter("days")).thenReturn("15");
//		when(request.getParameter("timeZone")).thenReturn("GMT-6:00");
//
//		servlet.doPost(request, response);
//		
//		verify(budgetDao).findActiveBudget(testUser);
//		verify(budgetDao).findTransactions(budget, 15, "GMT-6:00");
		
	}
	
	
}
