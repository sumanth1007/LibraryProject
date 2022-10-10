<%@page import="infinite.LibraryProject.LibraryDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
  <form action="Login.jsp" method="post">
   <center>
   <h2>Please Login</h2>
     User Name:
     <input type="text" name="userName" size="50"><br/><br/>
     Password:
     <input type="password" name="passCode" size="50"><br/><br/>
     <input type="submit" value="Login">
   </center>
  
  </form>
  <%
  
  if(request.getParameter("userName")!=null && request.getParameter("passCode")!=null){
	  LibraryDAO dao=new LibraryDAO();
	  String user=request.getParameter("userName");
	  String pwd=request.getParameter("passCode");
	  int count=dao.authenticate(user, pwd);
	  if(count==1){
		  session.setAttribute("user", user);
   %>
	  <jsp:forward page="Menu.jsp" />
			  
 <%}
 else{
	 out.println("InValid Credentials...");
 }
  } 
  %>
</body>
</html>