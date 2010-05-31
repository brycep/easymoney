package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class DeviceLoginServlet extends HttpServlet {
	
	private String createRequestUri(HttpServletRequest request)  {
		return request.getRequestURL().toString().replace("deviceLogin", "authenticate");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService userService = UserServiceFactory.getUserService();
		String requestUri = createRequestUri(request);
		String loginUrl = userService.createLoginURL(requestUri);
		response.getOutputStream().print(loginUrl);
	}

}
