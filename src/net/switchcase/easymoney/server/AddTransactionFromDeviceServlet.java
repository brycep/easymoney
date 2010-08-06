package net.switchcase.easymoney.server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import net.switchcase.easymoney.server.domain.Transaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class AddTransactionFromDeviceServlet extends EasyMoneyServlet {
	
	private static final Logger logger = Logger.getLogger(AddTransactionFromDeviceServlet.class.getName());
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private TransactionDao transactionDao;
	private BudgetDao budgetDao;
	private PersistenceManagerProvider pmProvider;
	
	@Inject
	public AddTransactionFromDeviceServlet(TransactionDao txDao, 
										   BudgetDao budgetDao,
										   PersistenceManagerProvider pmProvider)  {
		this.transactionDao = txDao;
		this.budgetDao = budgetDao;
		this.pmProvider = pmProvider;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		PersistenceManager pm = null;
		try  {
			pm = pmProvider.getPersistenceManager();

			String deviceKey = request.getParameter("deviceKey");
			Device device = budgetDao.findDevice(deviceKey, pm);
			
			if (null == device)  {
				printErrorXml(response, "InvalidDeviceKey");
				logger.warning("Device sent in an invalid device key:  " + deviceKey);
				return;
			}
			
			String description = request.getParameter("description"); 
			String gpsLat = request.getParameter("gpsLat");
			String gpsLong = request.getParameter("gpsLong");
			String amount = request.getParameter("amount");
			String source = request.getParameter("source");
			String createTimestamp = request.getParameter("createTimestamp");
			String transactionDate = request.getParameter("transactionDate");
			String reconsiled = request.getParameter("reconsiled");
			String cashEnvelopeKeyId = request.getParameter("cashEnvelopeKeyId");
			logger.info("Adding txn for Cash Envelope Id: " + cashEnvelopeKeyId);

			Budget budget = budgetDao.findActiveBudget(device.getUser(), pm);
			
			CashEnvelope expenseEnvelope = budget.getCashEnvelope(Long.parseLong(cashEnvelopeKeyId), logger);
			if (null == expenseEnvelope)  {
				printErrorXml(response, "CashEnvelopeNotFound");
				return;
			}

			Transaction txn = new Transaction();
			
			txn.setDescription(description);
			if (null != gpsLat)  {
				txn.setGpsLat(Double.parseDouble(gpsLat));
			}
			if (null != gpsLong)  {
				txn.setGpsLong(Double.parseDouble(gpsLong));
			}
			txn.setAmount(Long.parseLong(amount));
			txn.setSource(source);
			if (null != createTimestamp)  {
				txn.setCreateTimestamp(FORMAT.parse(createTimestamp));
			} else  {
				txn.setCreateTimestamp(new Date());
			}
			txn.setTransactionDate(FORMAT.parse(transactionDate));
			txn.setReconsiled("true".equals(reconsiled));
			txn.setCashEnvelopeKey(expenseEnvelope.getId());
			txn.setBudgetKey(budget.getId());
			txn.setCreatedByUser(device.getUser());
			
			try  {
				expenseEnvelope.subtractBalance(txn.getAmount());
				budgetDao.saveBudget(budget, pm);
				transactionDao.addTransaction(txn, pm);
				response.getWriter().print("OK");
				
			} catch(InsufficientFundsException exp)  {
				printErrorXml(response, "InsufficientBalance");
			}

		} catch(ParseException exp)  {
			logger.severe(exp.getMessage());
			response.getWriter().print("<results><result>ERROR</result><message>" + exp.getMessage() + "</message></results>");
		} finally {
			if (null != pm)  {
				pm.close();
			}
		}
		
	}
	

}
