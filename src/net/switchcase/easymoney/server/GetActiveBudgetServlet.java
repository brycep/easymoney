package net.switchcase.easymoney.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import flexjson.JSONSerializer;

@SuppressWarnings("serial")
@Singleton
public class GetActiveBudgetServlet extends EasyMoneyServlet {
	
	private final Logger log = Logger.getLogger(GetActiveBudgetServlet.class.getName());
	
	private BudgetDao budgetDao;
	private PersistenceManagerProvider pmProvider;
	
	@Inject
	public GetActiveBudgetServlet(BudgetDao budgetDao,
								  PersistenceManagerProvider pmProvider)  {
		this.budgetDao = budgetDao;
		this.pmProvider = pmProvider;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PersistenceManager pm = null;
		
		try  {
			pm = pmProvider.getPersistenceManager();

			String deviceKey = req.getParameter("deviceKey");
			Device device = budgetDao.findDevice(deviceKey, pm);
		
			Budget budget = budgetDao.findActiveBudget(device.getUser(), pm);
			JSONSerializer serializer = new JSONSerializer().include("envelopes")
				.include("monthlyBills").include("incomes");
			
			PrintWriter writer = resp.getWriter();
			String jsonResult = serializer.serialize(budget);
			log.fine(jsonResult);
			writer.print(jsonResult);
		} finally {
			pm.close();
		}
		
	}

}
