package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Device;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@SuppressWarnings("serial")
@RequestScoped
public class GetActiveBudgetServlet extends EasyMoneyServlet {
	
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
	
		budgetDao.findActiveBudget(device.getUser());
		
	}

}
