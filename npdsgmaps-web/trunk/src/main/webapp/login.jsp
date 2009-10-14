


<jsp:useBean id="ngjb" scope="session" class="gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.NPDSGmapJSPBean" />
<%
response.setHeader("Pragma","no-cache");
String username = request.getParameter("username");
String password = request.getParameter("password");
String passInKey = request.getParameter("passInKey");


String errorMessage = "";
	if (username !=null && username.length() < 15)
	{
		if (!session.getId().equals(passInKey))
		{
			out.println("error");
			return;
		}
		
		if (password != null && password.length() <15 && ngjb.authenticate(username, password))
		{
			 session.setAttribute("IS_AUTH", "true");
			/* RequestDispatcher rd = req.getRequestDispatcher(((HttpServletRequest) req).getContextPath()+ "/login.jsp");
				rd.forward(req, resp);*/
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/auth/gmap-pane.jsp?passInKey="+session.getId());
				//<jsp:forward page="auth/gmap-pane.jsp"></jsp:forward>
				%> <%
		}
		else 
		{
			errorMessage = "Invalid Username/Password.  Please try again.";
		}
	
	}
	else if (username != null && username.length() >= 15)
	{
		if (!session.getId().equals(passInKey))
		{
			out.println("error");
			return;
		}	
			username = username.substring(0, 15);
			errorMessage = "Invalid Username/Password.  Please try again.";
	}
	else 
	{
		username = "";
	}
 %>
<html>
<head>
<title>Quicksilver Login Page</title>
<LINK href="PCDA_files/pcda.css" type="text/css" rel="stylesheet">
</head>
<body>

<DIV id="wrapper">
<DIV id="pcdaHeader">
<H1>Quicksilver Login</H1> 
</DIV></DIV>
<br> <br>
<center>
<div id="loginPane" style="width: 600px;">
<H2 style="color:red"><%=errorMessage %></H2>
<form method="post" action="login.jsp">
Username: <input type="text" name="username" style="width:200px;" value="<%=username%>" maxlength="15">
<br>
Password: <input type="password" name="password" style="width:200px;">
<br>
<input type="hidden" name="passInKey" value="<%=session.getId() %>"/>
<INPUT TYPE="submit" id="submitbutton" value="submit" style="display:display;"/>
</form>
</div>
</center>
</body>
</html>