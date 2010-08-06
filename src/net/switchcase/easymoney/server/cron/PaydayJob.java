package net.switchcase.easymoney.server.cron;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.EasyMoneyServlet;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.PersistenceManagerProvider;
import net.switchcase.easymoney.server.domain.Budget;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PaydayJob extends EasyMoneyServlet {
	
	private static final long serialVersionUID = 110342419027268595L;
	private final Logger log = Logger.getLogger(PaydayJob.class.getName());

	private BudgetDao budgetDao;
	private PersistenceManagerProvider pmProvider;
	
	@Inject
	public PaydayJob(BudgetDao budgetDao, 
					 PersistenceManagerProvider pmProvider)  {
		this.budgetDao = budgetDao;
		this.pmProvider = pmProvider;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();

		PersistenceManager pm = null;
		try  {
			pm = pmProvider.getPersistenceManager();
			List<Budget> budgetList = budgetDao.findAllBudgets(pm);
			Date now = new Date();
			for(Budget budget : budgetList)  {
				budget.payday(now);
				budgetDao.saveBudget(budget, pm);
			}
			log.info("Ran Payday job successfully");
			writer.print("OK");
		} catch(Exception exp)  {
			log.severe(exp.getMessage());
			writer.print("ERROR - " + exp.getMessage());
		} finally {
			pm.close();
		}
		
	}

}
