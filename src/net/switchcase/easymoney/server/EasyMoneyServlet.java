package net.switchcase.easymoney.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EasyMoneyServlet extends HttpServlet {
	
	protected void printErrorXml(HttpServletResponse response, String errorKey) throws IOException  {
		response.getWriter().print(buildErrorMessage(errorKey));
	}
	
	protected String buildErrorMessage(String errorKey)  {
		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append("<results><result>").append(errorKey).append("</result></results>");
		return errorMessage.toString();
	}


}
