package net.switchcase.easymoney.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.InsufficientFundsException;
import net.switchcase.easymoney.server.domain.Transfer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class TransferCategoryBalanceServlet extends EasyMoneyServlet {
	
	private static final Logger logger = Logger.getLogger(TransferCategoryBalanceServlet.class.getName());

	private BudgetDao budgetDao;
	private TransactionDao transactionDao;
	private PersistenceManagerProvider pmProvider;
	
	@Inject
	public TransferCategoryBalanceServlet(BudgetDao budgetDao, 
										  TransactionDao transactionDao,
										  PersistenceManagerProvider pmProvider)  {
		this.budgetDao = budgetDao;
		this.transactionDao = transactionDao;
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
			
			if (null == device)  {
				printErrorXml(resp, "InvalidDeviceKey");
				return;
			}
	
			String sourceEnvelopeKeyId = req.getParameter("sourceEnvelopeKeyId");
			String destinationEnvelopeKeyId = req.getParameter("destinationEnvelopeKeyId");
			String source = req.getParameter("source");
			String amount = req.getParameter("amount");
			
			Budget budget = budgetDao.findActiveBudget(device.getUser(), pm);
			CashEnvelope sourceEnvelope = budget.getCashEnvelope(Long.parseLong(sourceEnvelopeKeyId), logger);
			if (null == sourceEnvelope)  {
				printErrorXml(resp, "InvalidSourceEnvelope");
				return;
			}
			
			CashEnvelope destEnvelope = budget.getCashEnvelope(Long.parseLong(destinationEnvelopeKeyId), logger);
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
				transactionDao.addTransfer(transfer, pm);
				
			} catch(InsufficientFundsException exp)  {
				printErrorXml(resp, "InsufficientFunds");
			}
			
			resp.getWriter().print("<results><result>OK</result></results>");
		} finally {
			pm.close();
		}
	}
		
}
