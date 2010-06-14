package net.switchcase.easymoney.server;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.JdoBudgetDao;
import net.switchcase.easymoney.server.dao.JdoTransactionDao;
import net.switchcase.easymoney.server.dao.TransactionDao;

import com.google.inject.servlet.ServletModule;

public class EasyMoneyServletModule extends ServletModule {

	@Override
    protected void configureServlets() {
        // cannot use @ImplementedBy
        bind(EasyMoneyService.class).to(EasyMoneyServiceImpl.class);
        bind(BudgetDao.class).to(JdoBudgetDao.class);
        bind(TransactionDao.class).to(JdoTransactionDao.class);

        serve("/easymoney/GWT.rpc").with(GuiceRemoteServiceServlet.class);
        serve("/deviceLogin").with(DeviceLoginServlet.class);
        serve("/authenticate").with(DeviceAuthenticatedServlet.class);
        serve("/addTransaction").with(AddTransactionFromDeviceServlet.class);
        
    }

}
