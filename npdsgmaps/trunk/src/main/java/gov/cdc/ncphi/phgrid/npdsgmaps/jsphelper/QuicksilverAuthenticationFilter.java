package gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.Filter;
import javax.servlet.ServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class QuicksilverAuthenticationFilter implements Filter {

	private static long reqCount;
	private static final Logger logger = Logger.getLogger(QuicksilverAuthenticationFilter.class);
/*	public void requestDestroyed(ServletRequestEvent sre) {


	}

	public void requestInitialized(ServletRequestEvent sre) {

	    ServletContext context = sre.getServletContext();
	    ServletRequest request = sre.getServletRequest();
	

	      logger.debug("Request for "
	              + (request instanceof HttpServletRequest ? ((HttpServletRequest) request)
	                  .getRequestURI()
	                  : "Unknown") + "; Count=" + ++reqCount);
	      


	}*/

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain fc) throws IOException, ServletException {
		
		logger.debug("I am inside the NPDSGmapAuthenticationFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpSession session = httpRequest.getSession(true);
		Object auth = session.getAttribute("IS_AUTH");
		
		if (auth == null)
		{			
			String contextPath = httpRequest.getContextPath();
			RequestDispatcher rd = req.getRequestDispatcher( "/login.jsp");
			rd.forward(req, resp);
		}
		else
		{
			fc.doFilter(req, resp);
		}
		
	}
	
	

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
