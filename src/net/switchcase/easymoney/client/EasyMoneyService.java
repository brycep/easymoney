package net.switchcase.easymoney.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * User: bryce
 * Date: May 2, 2010
 * Time: 8:52:38 PM
 */
@RemoteServiceRelativePath("budget")
public interface EasyMoneyService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use EasyMoneyService.App.getInstance() to access static instance of EasyMoneyServiceAsync
     */
    public static class App {
        private static final EasyMoneyServiceAsync ourInstance = (EasyMoneyServiceAsync) GWT.create(EasyMoneyService.class);

        public static EasyMoneyServiceAsync getInstance() {
            return ourInstance;
        }
    }

    void login(String userName, String password);
}
