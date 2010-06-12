package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Account;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.InsufficientFundsException;
import net.switchcase.easymoney.server.domain.Transaction;

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

		String sourceAccountKey = req.getParameter("sourceAccount");
		String destinationAccountKey = req.getParameter("destinationAccount");
		String amount = req.getParameter("amount");
		
		Budget budget = budgetDao.findActiveBudget(device.getUser());
		Account sourceAccount = budget.findAccount(sourceAccountKey);
		if (null == sourceAccount)  {
			printErrorXml(resp, "InvalidSourceAccount");
			return;
		}
		
		Account destAccount = budget.findAccount(destinationAccountKey);
		if (null == destAccount)  {
			printErrorXml(resp, "InvalidDestinationAccount");
			return;
		}
		
		Long amountValue = Long.parseLong(amount);
		try  {
			Transaction txn = budget.transfer(sourceAccount, destAccount, amountValue);
			transactionDao.addTransaction(txn);
			
		} catch(InsufficientFundsException exp)  {
			printErrorXml(resp, "InsufficientFunds");
		}
		
		resp.getWriter().print("<results><result>OK</result></results>");

	}
		
}
