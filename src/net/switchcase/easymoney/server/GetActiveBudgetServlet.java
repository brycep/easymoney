package net.switchcase.easymoney.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

import flexjson.JSONSerializer;

@SuppressWarnings("serial")
@RequestScoped
public class GetActiveBudgetServlet extends EasyMoneyServlet {
	
	private final Logger log = Logger.getLogger(GetActiveBudgetServlet.class.getName());
	
	private BudgetDao budgetDao;
	
	@Inject
	public GetActiveBudgetServlet(BudgetDao budgetDao)  {
		this.budgetDao = budgetDao;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String deviceKey = req.getParameter("deviceKey");
		Device device = budgetDao.findDevice(deviceKey);
	
		Budget budget = budgetDao.findActiveBudget(device.getUser());
		JSONSerializer serializer = new JSONSerializer().include("envelopes")
			.include("monthlyBills").include("incomes");
		
		PrintWriter writer = resp.getWriter();
		String jsonResult = serializer.serialize(budget);
		log.fine(jsonResult);
		writer.print(jsonResult);
		
	}

}
