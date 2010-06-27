package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.switchcase.easymoney.server.dao.BudgetDao;
import net.switchcase.easymoney.server.domain.Device;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("serial" )
@Singleton
public class DeviceAuthenticatedServlet extends HttpServlet {
	
	private BudgetDao budgetDao; 
	
	@Inject
	public DeviceAuthenticatedServlet(BudgetDao budgetDao)  {
		this.budgetDao = budgetDao;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (null == user)  {
			displayNotAuthenticatedException(resp);
		} else  {
			String deviceKey = createDeviceKey(user);
			displayAuthenticationDataAsHtml(user, deviceKey, resp);
		}
	}

	private void displayNotAuthenticatedException(HttpServletResponse resp) throws IOException {
		resp.getWriter().print("Not Authenticated");
	}
	
	private void displayAuthenticationDataAsHtml(User user, String deviceKey, HttpServletResponse resp) 
			throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		out.print("<html>");
		out.print("<head>");
		
		out.print("<script type=\"text/javascript\">");

		out.print("var userEmail=\"" + user.getEmail() + "\" \n");
		out.print("var nickname=\"" + user.getNickname() + "\" \n");
		out.print("var deviceKey=\"" + deviceKey + "\" \n");

		out.print("</script>");

		out.print("</head>");
		out.print("</html");
	}

	private String createDeviceKey(User user)  {
		Device device = new Device(user);
		budgetDao.saveDevice(device);
		return device.getId();
	}
}