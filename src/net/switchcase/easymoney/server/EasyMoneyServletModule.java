package net.switchcase.easymoney.server;

import net.switchcase.easymoney.client.EasyMoneyService;
import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.dao.JdoBudgetDao;

import com.google.inject.servlet.ServletModule;

public class EasyMoneyServletModule extends ServletModule {

	@Override
    protected void configureServlets() {
        serve("/easymoney/GWT.rpc").with(GuiceRemoteServiceServlet.class);
        serve("/deviceLogin").with(DeviceLoginServlet.class);
        serve("/authenticate").with(DeviceAuthenticatedServlet.class);
        serve("/addTransaction").with(AddTransactionFromDeviceServlet.class);

        // cannot use @ImplementedBy
        bind(EasyMoneyService.class).to(EasyMoneyServiceImpl.class);
        bind(BudgetDao.class).to(JdoBudgetDao.class);
        
    }

}
