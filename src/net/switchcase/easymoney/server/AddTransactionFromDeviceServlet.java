package net.switchcase.easymoney.server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.TransactionDao;
import net.switchcase.easymoney.server.domain.Device;
import net.switchcase.easymoney.server.domain.ExpenseCategory;
import net.switchcase.easymoney.server.domain.Transaction;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;

@SuppressWarnings({ "unused", "serial" })
@RequestScoped
public class AddTransactionFromDeviceServlet extends HttpServlet {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private TransactionDao transactionDao;
	private BudgetDao budgetDao;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	@Inject
	public AddTransactionFromDeviceServlet(TransactionDao txDao, 
										   BudgetDao budgetDao,
										   HttpServletRequest req,
										   HttpServletResponse resp)  {
		this.transactionDao = txDao;
		this.budgetDao = budgetDao;
		this.request = req;
		this.response = resp;
	}
	
	@Inject @RequestParameters 
	protected void doPost(Map<String, String[]> params)
			throws ServletException, IOException {
		
		String deviceKey = (null == params.get("deviceKey")) ? null : params.get("deviceKey")[0];
		Device device = budgetDao.findDevice(deviceKey);
		
		if (null == device)  {
			printInvalidKeyError();
			return;
		}
		
		String description = (null == params.get("description")) ? null : params.get("description")[0];
		String gpsLat = (null == params.get("gpsLat")) ? null : params.get("gpsLat")[0];
		String gpsLong = (null == params.get("gpsLong")) ? null : params.get("gpsLong")[0];
		String amount = (null == params.get("amount")) ? null : params.get("amount")[0];
		String source = (null == params.get("source")) ? null : params.get("source")[0];
		String createTimestamp = (null == params.get("createTimestamp")) ? null : params.get("createTimestamp")[0];
		String transactionDate = (null == params.get("transactionDate")) ? null : params.get("transactionDate")[0];
		String reconsiled = (null == params.get("reconsiled")) ? null : params.get("reconsiled")[0];
		String expenseCategoryKey = (null == params.get("expenseCategoryKey")) ? null : params.get("expenseCategoryKey")[0];
		
		try  {
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
			txn.setCreateTimestamp(FORMAT.parse(createTimestamp));
			txn.setTransactionDate(FORMAT.parse(transactionDate));
			txn.setReconsiled("true".equals(reconsiled));
			txn.setExpenseCategoryKey(expenseCategoryKey);

			ExpenseCategory expenseCategory = budgetDao.findExpenseCategory(expenseCategoryKey);
			if (null == expenseCategory)  {
				printExpenseCategoryNotFound();
				return;
			}
			
			if (expenseCategory.isSufficientFunds(txn.getAmount()))  {
				expenseCategory.subtractFromBalance(txn.getAmount());
				transactionDao.addTransaction(txn);
			} else  {
				printInsufficientFunds();
				return;
			}
			
			response.getWriter().print("<results><result>OK</result></results>");
		} catch(ParseException exp)  {
			response.getWriter().print("<results><result>ERROR</result><message>" + exp.getMessage() + "</message></results>");
		}
		
	}
	
	private void printInsufficientFunds() throws IOException  {
		response.getWriter().print("<results><result>InsufficientBalanceInCategory</result></results>");
	}
	
	private void printInvalidKeyError() throws IOException  {
		response.getWriter().print("<results><result>InvalidDeviceKey</result></results>");
	}
	
	private void printExpenseCategoryNotFound() throws IOException {
		response.getWriter().print("<results><result>ExpenseCategoryNotFound</result></results>");
	}
	

}
