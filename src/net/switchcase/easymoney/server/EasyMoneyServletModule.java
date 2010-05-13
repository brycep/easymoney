package net.switchcase.easymoney.server;

import net.switchcase.easymoney.client.EasyMoneyService;

import com.google.inject.servlet.ServletModule;

public class EasyMoneyServletModule extends ServletModule {

	@Override
    protected void configureServlets() {
        serve("/easymoney/GWT.rpc").with(GuiceRemoteServiceServlet.class);

        // cannot use @ImplementedBy
        bind(EasyMoneyService.class).to(EasyMoneyServiceImpl.class);
    }

}
