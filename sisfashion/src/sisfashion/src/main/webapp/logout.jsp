<%@ page session="true"%>
<%
	session.invalidate();

	String redirectURL = "/sisfashion";
    
	response.sendRedirect(redirectURL);
%>
