package net.switchcase.easymoney.server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
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

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private TransactionDao transactionDao;
	private BudgetDao budgetDao;
	
	@Inject
	public AddTransactionFromDeviceServlet(TransactionDao txDao, 
										   BudgetDao budgetDao)  {
		this.transactionDao = txDao;
		this.budgetDao = budgetDao;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String deviceKey = request.getParameter("deviceKey");
		Device device = budgetDao.findDevice(deviceKey);
		
		if (null == device)  {
			printErrorXml(response, "InvalidDeviceKey");
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
		String cashEnvelopeKey = request.getParameter("cashEnvelopeKey"); 
		
		try  {
			Budget budget = budgetDao.findActiveBudget(device.getUser());
			
			CashEnvelope expenseEnvelope = budget.getCashEnvelope(cashEnvelopeKey);
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
			txn.setCashEnvelopeKey(cashEnvelopeKey);
			txn.setBudgetKey(budget.getId());
			txn.setCreatedByUser(device.getUser());
			
			try  {
				expenseEnvelope.subtractBalance(txn.getAmount());
				transactionDao.addTransaction(txn);
				response.getWriter().print("<results><result>OK</result></results>");
				
			} catch(InsufficientFundsException exp)  {
				printErrorXml(response, "InsufficientBalance");
			}

		} catch(ParseException exp)  {
			response.getWriter().print("<results><result>ERROR</result><message>" + exp.getMessage() + "</message></results>");
		}
		
	}
	

}
