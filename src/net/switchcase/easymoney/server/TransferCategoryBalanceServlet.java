package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@SuppressWarnings("serial")
@RequestScoped
public class TransferCategoryBalanceServlet extends HttpServlet {

	private BudgetDao budgetDao;
	
	@Inject
	public TransferCategoryBalanceServlet(BudgetDao budgetDao)  {
		this.budgetDao = budgetDao;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String deviceKey = req.getParameter("deviceKey");
		Device device = budgetDao.findDevice(deviceKey);
		
		if (null == device)  {
			printErrorXml(resp, "InvalidDeviceKey");
			return;
		}

		String sourceCategoryKey = req.getParameter("sourceCategory");
		String destinationCategoryKey = req.getParameter("destinationCategory");
		String amount = req.getParameter("amount");
		
		ExpenseCategory sourceCategory = budgetDao.findExpenseCategory(sourceCategoryKey);
		if (null == sourceCategory)  {
			printErrorXml(resp, "InvalidSourceCategory");
			return;
		}
		
		ExpenseCategory destCategory = budgetDao.findExpenseCategory(destinationCategoryKey);
		if (null == destCategory)  {
			printErrorXml(resp, "InvalidDestinationCategory");
			return;
		}
		
		Long amountValue = Long.parseLong(amount);
		if (sourceCategory.getBalance() < amountValue)  {
			printErrorXml(resp, "NotEnoughMoneyInSourceCategory");
		}
		
		sourceCategory.subtractFromBalance(amountValue);
		destCategory.addToBalance(amountValue);
		
		resp.getWriter().print("<results><result>OK</result></results>");

	}
		
	private void printErrorXml(HttpServletResponse response, String errorKey) throws IOException  {
		response.getWriter().print(buildErrorMessage(errorKey));
	}
	
	private String buildErrorMessage(String errorKey)  {
		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append("<results><result>").append(errorKey).append("</result></results>");
		return errorMessage.toString();
	}

}
