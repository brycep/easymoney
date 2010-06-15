package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.InsufficientFundsException;
import net.switchcase.easymoney.server.domain.Transfer;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@SuppressWarnings("serial")
@RequestScoped
public class TransferCategoryBalanceServlet extends EasyMoneyServlet {

	private BudgetDao budgetDao;
	private TransactionDao transactionDao;
	
	@Inject
	public TransferCategoryBalanceServlet(BudgetDao budgetDao, TransactionDao transactionDao)  {
		this.budgetDao = budgetDao;
		this.transactionDao = transactionDao;
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

		String sourceEnvelopeKey = req.getParameter("sourceEnvelope");
		String destinationEnvelopeKey = req.getParameter("destinationEnvelope");
		String source = req.getParameter("source");
		String amount = req.getParameter("amount");
		
		Budget budget = budgetDao.findActiveBudget(device.getUser());
		CashEnvelope sourceEnvelope = budget.getCashEnvelope(sourceEnvelopeKey);
		if (null == sourceEnvelope)  {
			printErrorXml(resp, "InvalidSourceEnvelope");
			return;
		}
		
		CashEnvelope destEnvelope = budget.getCashEnvelope(destinationEnvelopeKey);
		if (null == destEnvelope)  {
			printErrorXml(resp, "InvalidDestinationEnvelope");
			return;
		}
		
		Long amountValue = Long.parseLong(amount);
		try  {
			Transfer transfer = budget.transfer(sourceEnvelope,  
												destEnvelope, 
												amountValue,
												source,
												device.getUser());
			transactionDao.addTransfer(transfer);
			
		} catch(InsufficientFundsException exp)  {
			printErrorXml(resp, "InsufficientFunds");
		}
		
		resp.getWriter().print("<results><result>OK</result></results>");

	}
		
}
